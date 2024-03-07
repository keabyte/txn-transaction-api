package com.keabyte.transaction_engine.transaction_api.web

import com.keabyte.transaction_engine.client_api.exception.BusinessException
import com.keabyte.transaction_engine.transaction_api.type.BalanceEffectType
import com.keabyte.transaction_engine.transaction_api.web.fixture.AccountFixture
import com.keabyte.transaction_engine.transaction_api.web.model.CreateDepositRequest
import com.keabyte.transaction_engine.transaction_api.web.model.CreateWithdrawalRequest
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import jakarta.inject.Inject
import jakarta.ws.rs.core.MediaType
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

@QuarkusTest
class TransactionControllerTest {

    @Inject
    lateinit var transactionController: TransactionController

    @Test
    fun `create deposit`() {
        val transaction = transactionController.createDeposit(
            CreateDepositRequest(
                accountNumber = AccountFixture.defaultAccountNumber,
                amount = BigDecimal("100.33"),
                currency = "AUD"
            )
        )

        assertThat(transaction).isNotNull
        assertThat(transaction.accountTransactions).hasSize(1)

        val accountTransaction = transaction.accountTransactions[0]
        assertThat(accountTransaction.invesmentTransactions).hasSize(1)
        assertThat(accountTransaction.accountNumber).isEqualTo(AccountFixture.defaultAccountNumber)

        val investmentTransaction = accountTransaction.invesmentTransactions[0]
        assertThat(investmentTransaction.amount).isEqualTo(BigDecimal("100.33"))
        assertThat(investmentTransaction.currency).isEqualTo("AUD")
        assertThat(investmentTransaction.balanceEffectType).isEqualTo(BalanceEffectType.CREDIT)
    }

    @Test
    fun `create deposit when account does not exist`() {
        assertThrows<BusinessException> {
            transactionController.createDeposit(
                CreateDepositRequest(
                    accountNumber = "not a real account number",
                    amount = BigDecimal("100.33"),
                    currency = "AUD"
                )
            )
        }
    }

    @Test
    fun `create deposit via REST API`() {
        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CreateDepositRequest(
                    accountNumber = "P1000",
                    amount = BigDecimal("100.33"),
                    currency = "AUD"
                )
            )
            .`when`()
            .post("/transactions/deposits")
            .then()
            .statusCode(200)
            .body("accountNumber", equalTo(AccountFixture.defaultAccountNumber))
    }

    @Test
    fun `create withdrawal`() {
        val transaction = transactionController.createWithdrawal(
            CreateWithdrawalRequest(
                accountNumber = AccountFixture.defaultAccountNumber,
                amount = BigDecimal("100.33"),
                currency = "AUD"
            )
        )

        assertThat(transaction).isNotNull
        assertThat(transaction.accountTransactions).hasSize(1)

        val accountTransaction = transaction.accountTransactions[0]
        assertThat(accountTransaction.invesmentTransactions).hasSize(1)
        assertThat(accountTransaction.accountNumber).isEqualTo(AccountFixture.defaultAccountNumber)

        val investmentTransaction = accountTransaction.invesmentTransactions[0]
        assertThat(investmentTransaction.amount).isEqualTo(BigDecimal("100.33"))
        assertThat(investmentTransaction.currency).isEqualTo("AUD")
        assertThat(investmentTransaction.balanceEffectType).isEqualTo(BalanceEffectType.DEBIT)
    }
}