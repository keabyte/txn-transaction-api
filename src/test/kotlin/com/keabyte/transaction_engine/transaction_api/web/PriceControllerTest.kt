package com.keabyte.transaction_engine.transaction_api.web

import com.keabyte.transaction_engine.client_api.exception.BusinessException
import com.keabyte.transaction_engine.transaction_api.web.fixture.TestDataFixture
import com.keabyte.transaction_engine.transaction_api.web.model.CreatePriceRequest
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.time.OffsetDateTime

@QuarkusTest
class PriceControllerTest {

    @Inject
    lateinit var priceController: PriceController

    @Test
    fun `create price`() {
        val price = priceController.createPrice(
            TestDataFixture.defaultAssetCode, CreatePriceRequest(
                effectiveDate = OffsetDateTime.now(),
                price = BigDecimal.TEN,
                currency = "AUD"
            )
        )

        assertThat(price.price).isEqualTo(BigDecimal.TEN)
        assertThat(price.currency).isEqualTo("AUD")
    }

    @Test
    fun `create price when asset does not exist`() {
        assertThat(assertThrows<BusinessException> {
            priceController.createPrice(
                "not a real asset code", CreatePriceRequest(
                    effectiveDate = OffsetDateTime.now(),
                    price = BigDecimal.TEN,
                    currency = "AUD"
                )
            )
        }.message).contains("No asset exists")
    }

    @Test
    fun `get latest price`() {
        priceController.createPrice(
            TestDataFixture.defaultAssetCode, CreatePriceRequest(
                effectiveDate = OffsetDateTime.now().minusDays(1),
                price = BigDecimal.TEN,
                currency = "AUD"
            )
        )

        priceController.createPrice(
            TestDataFixture.defaultAssetCode, CreatePriceRequest(
                effectiveDate = OffsetDateTime.now(),
                price = BigDecimal.ONE,
                currency = "AUD"
            )
        )

        val price = priceController.getLatestPrice(TestDataFixture.defaultAssetCode)

        assertThat(price.price).isEqualTo(BigDecimal.ONE)
        assertThat(price.currency).isEqualTo("AUD")
    }
}