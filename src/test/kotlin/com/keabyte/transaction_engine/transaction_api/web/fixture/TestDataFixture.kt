package com.keabyte.transaction_engine.transaction_api.web.fixture

import com.keabyte.transaction_engine.transaction_api.repository.entity.AccountEntity
import com.keabyte.transaction_engine.transaction_api.repository.entity.AssetEntity
import io.quarkus.runtime.Startup
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import java.math.BigDecimal
import java.time.OffsetDateTime

@ApplicationScoped
class TestDataFixture {

    companion object {
        const val defaultAccountNumber = "P1000"
        const val defaultAssetCode = "A1000"
    }

    @Startup
    fun init() {
        createTestAccounts()
        createTestAssets()
    }

    @Transactional
    fun createTestAccounts() {
        AccountEntity(
            accountNumber = "P1000",
            dateCreated = OffsetDateTime.now(),
            clientNumber = "C1000"
        ).persist()
    }

    @Transactional
    fun createTestAssets() {
        AssetEntity(
            assetCode = "A1000",
            createdDate = OffsetDateTime.now(),
            foundedDate = OffsetDateTime.now(),
            dividendYield = BigDecimal(0.05),
            description = "Test Asset",
            websiteUrl = "https://example.com"
        ).persist()
    }
}