package com.keabyte.transaction_engine.transaction_api.web

import com.keabyte.transaction_engine.client_api.exception.BusinessException
import com.keabyte.transaction_engine.transaction_api.type.AssetType
import com.keabyte.transaction_engine.transaction_api.web.fixture.TestDataFixture
import com.keabyte.transaction_engine.transaction_api.web.model.asset.CreateAssetRequest
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.time.OffsetDateTime

@QuarkusTest
class AssetControllerTest {

    @Inject
    lateinit var assetController: AssetController

    @Test
    fun `get asset by code`() {
        val asset = assetController.findByAssetCode(TestDataFixture.defaultAssetCode)
        assertThat(asset.assetCode).isEqualTo(TestDataFixture.defaultAssetCode)
    }

    @Test
    fun `get asset by code when asset does not exist`() {
        assertThat(assertThrows<BusinessException> {
            assetController.findByAssetCode("not a real asset code")
        }.message).contains("No asset exists")
    }

    @Test
    fun `create asset`() {
        assetController.createAsset(
            CreateAssetRequest(
                assetCode = "TSLA",
                name = "Tesla Inc.",
                foundedDate = OffsetDateTime.now(),
                dividendYield = BigDecimal.ZERO,
                description = "Tesla",
                websiteUrl = "https://www.tesla.com",
                type = AssetType.STOCK,
                roundingScale = 6,
                currency = "AUD"
            )
        )

        val asset = assetController.findByAssetCode("TSLA")
        assertThat(asset.assetCode).isEqualTo("TSLA")
    }
}