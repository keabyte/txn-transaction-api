package com.keabyte.transaction_engine.transaction_api.service

import com.keabyte.transaction_engine.transaction_api.config.round
import com.keabyte.transaction_engine.transaction_api.repository.entity.AccountTransactionEntity
import com.keabyte.transaction_engine.transaction_api.repository.entity.BalanceEntity
import com.keabyte.transaction_engine.transaction_api.repository.entity.InvestmentTransactionEntity
import com.keabyte.transaction_engine.transaction_api.repository.entity.TransactionEventEntity
import com.keabyte.transaction_engine.transaction_api.service.dto.AccountValuationDTO
import com.keabyte.transaction_engine.transaction_api.service.dto.BalanceValuationDTO
import com.keabyte.transaction_engine.transaction_api.service.dto.CreateInvestmentParameters
import com.keabyte.transaction_engine.transaction_api.service.dto.CreateTransactionParameters
import com.keabyte.transaction_engine.transaction_api.type.BalanceEffectType
import com.keabyte.transaction_engine.transaction_api.type.TransactionType
import com.keabyte.transaction_engine.transaction_api.web.model.transaction.CreateDepositRequest
import com.keabyte.transaction_engine.transaction_api.web.model.transaction.CreateWithdrawalRequest
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import java.math.BigDecimal

@ApplicationScoped
class TransactionService(
    private val accountService: AccountService,
    private val assetService: AssetService,
) {
    fun createTransaction(params: CreateTransactionParameters): TransactionEventEntity {
        val transaction = TransactionEventEntity(
            type = params.transactionType
        )

        val accountNumbers = params.investments.map { it.accountNumber }.toSet()

        for (accountNumber in accountNumbers) {
            val account = accountService.getByAccountNumber(accountNumber)
            val accountValuation = accountService.getAccountValuation(account)

            val accountTransaction = AccountTransactionEntity(
                transactionEvent = transaction,
                account = account
            )
            transaction.accountTransactions.add(accountTransaction)

            for (investment in params.investments.filter { it.accountNumber == accountNumber }) {
                val investmentTransaction =
                    createInvestmentTransaction(investment, accountTransaction, accountValuation)
                accountTransaction.investmentTransactions.add(investmentTransaction)
            }
        }

        return transaction
    }

    private fun createInvestmentTransaction(
        investment: CreateInvestmentParameters,
        accountTransaction: AccountTransactionEntity,
        accountValuation: AccountValuationDTO
    ): InvestmentTransactionEntity {
        val asset = assetService.findByAssetCode(investment.assetCode)
        val units = (investment.amount / asset.latestPrice).round(asset.roundingScale)

        val investmentTransaction = InvestmentTransactionEntity(
            accountTransaction = accountTransaction,
            amount = investment.amount,
            currency = investment.currency,
            balanceEffectType = investment.balanceEffectType,
            asset = asset,
            units = units
        )

        if (!accountValuation.hasAssetBalance(investmentTransaction.asset.assetCode)) {
            val balance = BalanceEntity(
                account = accountTransaction.account,
                asset = investmentTransaction.asset,
                units = BigDecimal.ZERO
            )
            accountValuation.balances.add(BalanceValuationDTO(balance, asset.latestPrice))
            balance.persist()
        }

        val unitAdjustment =
            investmentTransaction.units * BalanceEffectType.toBalanceEffectMultiplier(investmentTransaction.balanceEffectType)
                .toBigDecimal()
        accountValuation.adjustAssetUnits(investmentTransaction.asset.assetCode, unitAdjustment)


        return investmentTransaction
    }

    @Transactional
    fun createDeposit(request: CreateDepositRequest): TransactionEventEntity {
        val transaction = createTransaction(
            CreateTransactionParameters(
                transactionType = TransactionType.DEPOSIT,
                investments = listOf(
                    CreateInvestmentParameters(
                        accountNumber = request.accountNumber,
                        amount = request.amount,
                        currency = request.currency,
                        balanceEffectType = BalanceEffectType.CREDIT,
                        assetCode = assetService.findCashAssetForCurrency(request.currency).assetCode
                    )
                )
            )
        )
        transaction.persist()
        return transaction
    }

    @Transactional
    fun createWithdrawal(request: CreateWithdrawalRequest): TransactionEventEntity {
        val transaction = createTransaction(
            CreateTransactionParameters(
                transactionType = TransactionType.WITHDRAWAL,
                investments = listOf(
                    CreateInvestmentParameters(
                        accountNumber = request.accountNumber,
                        amount = request.amount,
                        currency = request.currency,
                        balanceEffectType = BalanceEffectType.DEBIT,
                        assetCode = assetService.findCashAssetForCurrency(request.currency).assetCode
                    )
                )
            )
        )
        transaction.persist()
        return transaction
    }
}
