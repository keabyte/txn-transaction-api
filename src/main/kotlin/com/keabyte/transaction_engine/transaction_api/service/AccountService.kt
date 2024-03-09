package com.keabyte.transaction_engine.transaction_api.service

import com.keabyte.transaction_engine.transaction_api.exception.BusinessException
import com.keabyte.transaction_engine.transaction_api.repository.AccountRepository
import com.keabyte.transaction_engine.transaction_api.repository.BalanceRepository
import com.keabyte.transaction_engine.transaction_api.repository.entity.AccountEntity
import com.keabyte.transaction_engine.transaction_api.service.dto.AccountValuation
import com.keabyte.transaction_engine.transaction_api.service.dto.BalanceValuation
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class AccountService(
    private val accountRepository: AccountRepository,
    private val balanceRepository: BalanceRepository,
    private val priceService: PriceService
) {

    @Transactional
    fun getByAccountNumber(accountNumber: String): AccountEntity {
        return accountRepository.findByAccountNumber(accountNumber)
            ?: throw BusinessException("No account exists with account number $accountNumber")
    }

    @Transactional
    fun getAccountValuation(account: AccountEntity): AccountValuation {
        val accountValuation = AccountValuation()
        val balances = balanceRepository.findByAccountId(account.id!!)

        for (balance in balances) {
            val price = priceService.getLatestPriceForAsset(balance.asset.assetCode)
            accountValuation.balances.add(BalanceValuation(balance, price))
        }

        return accountValuation
    }
}