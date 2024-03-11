package com.keabyte.transaction_engine.transaction_api.service

import com.keabyte.transaction_engine.transaction_api.exception.BusinessException
import com.keabyte.transaction_engine.transaction_api.repository.AccountRepository
import com.keabyte.transaction_engine.transaction_api.repository.BalanceRepository
import com.keabyte.transaction_engine.transaction_api.repository.entity.AccountEntity
import com.keabyte.transaction_engine.transaction_api.service.dto.AccountValuationDTO
import com.keabyte.transaction_engine.transaction_api.service.dto.BalanceValuationDTO
import com.keabyte.transaction_engine.transaction_api.web.model.Account
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import java.math.BigDecimal

@ApplicationScoped
class AccountService(
    private val accountRepository: AccountRepository,
    private val balanceRepository: BalanceRepository,
) {

    @Transactional
    fun getByAccountNumber(accountNumber: String): AccountEntity {
        return accountRepository.findByAccountNumber(accountNumber)
            ?: throw BusinessException("No account exists with account number $accountNumber")
    }

    @Transactional
    fun getAccountValuation(account: AccountEntity): AccountValuationDTO {
        val accountValuation = AccountValuationDTO()
        val balances = balanceRepository.findByAccountId(account.id!!)

        for (balance in balances) {
            val price = balance.asset.latestPrice
            if (price == null) {
                Log.warn("No price found for asset ${balance.asset.assetCode}")
            }
            accountValuation.balances.add(BalanceValuationDTO(balance, price ?: BigDecimal.ZERO))
        }

        return accountValuation
    }

    fun getAccountValuation(accountNumber: String): AccountValuationDTO {
        val account = getByAccountNumber(accountNumber)
        return getAccountValuation(account)
    }

    @Transactional
    fun processAccountEvent(account: Account) {
        Log.info("Processing account event: $account")
        AccountEntity(
            accountNumber = account.accountNumber,
            clientNumber = account.clientNumber,
            createdDate = account.createdDate
        ).persist()
    }
}