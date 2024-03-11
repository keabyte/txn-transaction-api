package com.keabyte.transaction_engine.transaction_api.fixture

import com.keabyte.transaction_engine.transaction_api.repository.entity.AccountEntity
import com.keabyte.transaction_engine.transaction_api.repository.entity.AssetEntity
import com.keabyte.transaction_engine.transaction_api.type.AssetType
import io.quarkus.runtime.Startup
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*

@ApplicationScoped
class TestDataFixture {

    companion object {
        const val defaultAccountNumber = "P1000"
        const val zeroBalanceAccountNumber = "P0000"
        const val defaultAssetCode = "CASH_AUD"
        const val noPriceAssetCode = "CASH_GBP"
    }

    @Startup
    fun init() {
        createTestAccounts()
        createTestAssets()
    }

    @Transactional
    fun getNewAccount(clientNumber: String = UUID.randomUUID().toString()): AccountEntity {
        val account = AccountEntity(
            accountNumber = UUID.randomUUID().toString(),
            createdDate = OffsetDateTime.now(),
            clientNumber = clientNumber
        )
        account.persist()

        return account
    }

    @Transactional
    fun createTestAccounts() {
        AccountEntity(
            accountNumber = defaultAccountNumber,
            createdDate = OffsetDateTime.now(),
            clientNumber = "C1000"
        ).persist()

        AccountEntity(
            accountNumber = zeroBalanceAccountNumber,
            createdDate = OffsetDateTime.now(),
            clientNumber = "C1000"
        ).persist()
    }

    @Transactional
    fun createTestAssets() {
        val asset = AssetEntity(
            assetCode = "CASH_AUD",
            name = "Cash (AUD)",
            dividendYield = BigDecimal.ZERO,
            type = AssetType.CASH,
            roundingScale = 2,
            currency = "AUD",
            latestPrice = BigDecimal.ONE
        )
        asset.persist()

        AssetEntity(
            assetCode = "CASH_USD",
            name = "Cash (USD)",
            dividendYield = BigDecimal.ZERO,
            type = AssetType.CASH,
            roundingScale = 2,
            currency = "USD",
            latestPrice = BigDecimal.ONE
        ).persist()

        AssetEntity(
            assetCode = "CASH_GBP",
            name = "Cash (GBP)",
            dividendYield = BigDecimal.ZERO,
            type = AssetType.CASH,
            roundingScale = 2,
            currency = "GBP",
        ).persist()
    }
}