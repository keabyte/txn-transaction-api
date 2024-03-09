package com.keabyte.transaction_engine.transaction_api.service

import com.keabyte.transaction_engine.transaction_api.fixture.TestDataFixture
import com.keabyte.transaction_engine.transaction_api.web.model.transaction.CreateDepositRequest
import com.keabyte.transaction_engine.transaction_api.web.model.transaction.CreateWithdrawalRequest
import io.quarkus.test.TestTransaction
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

@QuarkusTest
class BalanceServiceTest() {

    @Inject
    lateinit var accountService: AccountService

    @Inject
    lateinit var transactionService: TransactionService

    @Test
    fun `get account balance when no transactions exist`() {
        val account = accountService.getByAccountNumber(TestDataFixture.defaultAccountNumber)
        val accountValuation = accountService.getAccountValuation(account)
        assertEquals(BigDecimal.ZERO, accountValuation.totalValue())
    }

    @TestTransaction
    @Test
    fun `get account balance given a transaction`() {
        val account = accountService.getByAccountNumber(TestDataFixture.defaultAccountNumber)
        transactionService.createDeposit(
            CreateDepositRequest(
                accountNumber = TestDataFixture.defaultAccountNumber,
                amount = BigDecimal("99.33"),
                currency = "AUD"
            )
        )

        transactionService.createWithdrawal(
            CreateWithdrawalRequest(
                accountNumber = TestDataFixture.defaultAccountNumber,
                amount = BigDecimal("0.33"),
                currency = "AUD"
            )
        )


        val accountValuation = accountService.getAccountValuation(account)
        assertEquals(BigDecimal("99.00"), accountValuation.totalValue())
    }
}