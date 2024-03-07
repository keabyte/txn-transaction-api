package com.keabyte.transaction_engine.transaction_api.web

import com.keabyte.transaction_engine.client_api.exception.BusinessException
import com.keabyte.transaction_engine.transaction_api.web.fixture.TestDataFixture
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@QuarkusTest
class AssetControllerTest {

    @Inject
    lateinit var assetController: AssetController

    @Test
    fun `get asset by code`() {
        val asset = assetController.findByAssetCode(TestDataFixture.defaultAssetCode)
    }

    @Test
    fun `get asset by code when asset does not exist`() {
        assertThrows<BusinessException> {
            assetController.findByAssetCode("INVALID")
        }
    }
}