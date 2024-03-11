package com.keabyte.transaction_engine.transaction_api.web

import com.keabyte.transaction_engine.transaction_api.exception.BusinessException
import com.keabyte.transaction_engine.transaction_api.fixture.TestDataFixture
import com.keabyte.transaction_engine.transaction_api.type.BalanceEffectType
import com.keabyte.transaction_engine.transaction_api.web.model.transaction.CreateDepositRequest
import com.keabyte.transaction_engine.transaction_api.web.model.transaction.CreateWithdrawalRequest
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import jakarta.inject.Inject
import jakarta.ws.rs.core.MediaType
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.Test
import java.math.BigDecimal

@QuarkusTest
class TransactionControllerTest {

    @Inject
    lateinit var transactionController: TransactionController

    @Test
    fun `create deposit`() {
        val transaction = transactionController.createDeposit(
            CreateDepositRequest(
                accountNumber = TestDataFixture.defaultAccountNumber,
                amount = BigDecimal("100.33"),
                currency = "AUD"
            )
        )

        assertThat(transaction).isNotNull
        assertThat(transaction.accountTransactions).hasSize(1)

        val accountTransaction = transaction.accountTransactions[0]
        assertThat(accountTransaction.invesmentTransactions).hasSize(1)
        assertThat(accountTransaction.accountNumber).isEqualTo(TestDataFixture.defaultAccountNumber)

        val investmentTransaction = accountTransaction.invesmentTransactions[0]
        assertThat(investmentTransaction.amount).isEqualTo(BigDecimal("100.33"))
        assertThat(investmentTransaction.currency).isEqualTo("AUD")
        assertThat(investmentTransaction.balanceEffectType).isEqualTo(BalanceEffectType.CREDIT)
        assertThat(investmentTransaction.units).isEqualTo(BigDecimal("100.33"))
    }

    @Test
    fun `create deposit when account does not exist`() {
        assertThatThrownBy {
            transactionController.createDeposit(
                CreateDepositRequest(
                    accountNumber = "not a real account number",
                    amount = BigDecimal("100.33"),
                    currency = "AUD"
                )
            )
        }
            .isInstanceOf(BusinessException::class.java)
            .hasMessageContaining("No account exists")
    }

    @Test
    fun `create deposit when no cash asset exists`() {
        assertThatThrownBy {
            transactionController.createDeposit(
                CreateDepositRequest(
                    accountNumber = TestDataFixture.defaultAccountNumber,
                    amount = BigDecimal("100"),
                    currency = "NZD"
                )
            )
        }
            .isInstanceOf(BusinessException::class.java)
            .hasMessageContaining("No cash asset exists for currency")
    }

    @Test
    fun `create deposit when asset has no price`() {
        assertThatThrownBy {
            transactionController.createDeposit(
                CreateDepositRequest(
                    accountNumber = TestDataFixture.defaultAccountNumber,
                    amount = BigDecimal("100"),
                    currency = "GBP"
                )
            )
        }
            .isInstanceOf(BusinessException::class.java)
            .hasMessageContaining("No price found for asset")
    }

    @Test
    fun `create deposit rest call`() {
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
            .body("type", equalTo("DEPOSIT"))
            .body("transactionReference", notNullValue())
            .body("accountTransactions", notNullValue())
    }


    @Test
    fun `create withdrawal`() {
        val transaction = transactionController.createWithdrawal(
            CreateWithdrawalRequest(
                accountNumber = TestDataFixture.defaultAccountNumber,
                amount = BigDecimal("100.33"),
                currency = "AUD"
            )
        )

        assertThat(transaction).isNotNull
        assertThat(transaction.accountTransactions).hasSize(1)

        val accountTransaction = transaction.accountTransactions[0]
        assertThat(accountTransaction.invesmentTransactions).hasSize(1)
        assertThat(accountTransaction.accountNumber).isEqualTo(TestDataFixture.defaultAccountNumber)

        val investmentTransaction = accountTransaction.invesmentTransactions[0]
        assertThat(investmentTransaction.amount).isEqualTo(BigDecimal("100.33"))
        assertThat(investmentTransaction.currency).isEqualTo("AUD")
        assertThat(investmentTransaction.balanceEffectType).isEqualTo(BalanceEffectType.DEBIT)
    }
}