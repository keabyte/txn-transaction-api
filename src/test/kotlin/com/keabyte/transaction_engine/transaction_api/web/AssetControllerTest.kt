package com.keabyte.transaction_engine.transaction_api.web

import com.keabyte.transaction_engine.transaction_api.exception.BusinessException
import com.keabyte.transaction_engine.transaction_api.fixture.TestDataFixture
import com.keabyte.transaction_engine.transaction_api.type.AssetType
import com.keabyte.transaction_engine.transaction_api.web.model.asset.CreateAssetRequest
import io.quarkus.test.TestTransaction
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
class AssetControllerTest {

    @Inject
    lateinit var assetController: AssetController

    val createAssetRequest = CreateAssetRequest(
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

    @TestTransaction
    @Test
    fun `create asset`() {
        assetController.createAsset(createAssetRequest)

        val asset = assetController.findByAssetCode("TSLA")
        assertThat(asset.assetCode).isEqualTo("TSLA")
    }

    @TestTransaction
    @Test
    fun `create asset rest call`() {
        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(createAssetRequest)
            .`when`()
            .post("/assets")
            .then()
            .statusCode(200)
            .body("assetCode", equalTo("TSLA"))
    }
}