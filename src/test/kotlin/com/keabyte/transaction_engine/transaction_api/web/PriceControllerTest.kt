package com.keabyte.transaction_engine.transaction_api.web

import com.keabyte.transaction_engine.transaction_api.exception.BusinessException
import com.keabyte.transaction_engine.transaction_api.fixture.TestDataFixture
import com.keabyte.transaction_engine.transaction_api.web.model.transaction.CreatePriceRequest
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import jakarta.inject.Inject
import jakarta.ws.rs.core.MediaType
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.equalTo
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

    @Test
    fun `get latest price when asset does not exist`() {
        assertThat(assertThrows<BusinessException> {
            priceController.getLatestPrice("not a real asset code")
        }.message).contains("No price exists for asset")
    }

    @Test
    fun `get latest price when no price exists`() {
        assertThat(assertThrows<BusinessException> {
            priceController.getLatestPrice("CASH_USD")
        }.message).contains("No price exists for asset")
    }

    @Test
    fun `get latest price rest call`() {
        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON)
            .`when`()
            .get("/assets/${TestDataFixture.defaultAssetCode}/prices/latest")
            .then()
            .statusCode(200)
            .body("currency", equalTo("AUD"))
    }
}