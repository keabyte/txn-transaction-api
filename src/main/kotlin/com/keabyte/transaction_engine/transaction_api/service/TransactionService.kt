package com.keabyte.transaction_engine.transaction_api.service

import com.keabyte.transaction_engine.transaction_api.exception.BusinessException
import com.keabyte.transaction_engine.transaction_api.repository.AccountRepository
import com.keabyte.transaction_engine.transaction_api.repository.entity.AccountTransactionEntity
import com.keabyte.transaction_engine.transaction_api.repository.entity.InvestmentTransactionEntity
import com.keabyte.transaction_engine.transaction_api.repository.entity.TransactionEventEntity
import com.keabyte.transaction_engine.transaction_api.type.BalanceEffectType
import com.keabyte.transaction_engine.transaction_api.type.TransactionType
import com.keabyte.transaction_engine.transaction_api.web.model.transaction.CreateDepositRequest
import com.keabyte.transaction_engine.transaction_api.web.model.transaction.CreateWithdrawalRequest
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import java.math.BigDecimal

@ApplicationScoped
class TransactionService(
    private val accountRepository: AccountRepository,
    private val assetService: AssetService,
    private val priceService: PriceService
) {
    fun createTransaction(params: CreateTransactionParameters): TransactionEventEntity {
        val transaction = TransactionEventEntity(
            type = params.transactionType
        )

        val accountNumbers = params.investments.map { it.accountNumber }.toSet()

        for (accountNumber in accountNumbers) {
            val account = accountRepository.findByAccountNumber(accountNumber)
                ?: throw BusinessException("No account exists with account number $accountNumber")

            val accountTransaction = AccountTransactionEntity(
                transactionEvent = transaction,
                account = account
            )
            transaction.accountTransactions.add(accountTransaction)

            for (investment in params.investments.filter { it.accountNumber == accountNumber }) {
                val investmentTransaction = createInvestmentTransaction(investment, accountTransaction)
                accountTransaction.investmentTransactions.add(investmentTransaction)
            }
        }

        return transaction
    }

    private fun createInvestmentTransaction(
        investment: CreateInvestmentParameters,
        accountTransaction: AccountTransactionEntity
    ): InvestmentTransactionEntity {
        val asset = assetService.findByAssetCode(investment.assetCode)
        val price = priceService.getLatestPriceForAsset(investment.assetCode)
        val units = (investment.amount / price.price).setScale(asset.roundingScale, BigDecimal.ROUND_HALF_UP)

        return InvestmentTransactionEntity(
            accountTransaction = accountTransaction,
            amount = investment.amount,
            currency = investment.currency,
            balanceEffectType = investment.balanceEffectType,
            asset = asset,
            units = units
        )
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