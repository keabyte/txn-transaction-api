package com.keabyte.transaction_engine.transaction_api.service

import com.keabyte.transaction_engine.transaction_api.fixture.TestDataFixture
import com.keabyte.transaction_engine.transaction_api.web.model.transaction.CreateDepositRequest
import com.keabyte.transaction_engine.transaction_api.web.model.transaction.CreateWithdrawalRequest
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import jakarta.inject.Inject
import jakarta.ws.rs.core.MediaType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

@QuarkusTest
class BalanceServiceTest {

    @Inject
    lateinit var testDataFixture: TestDataFixture

    @Inject
    lateinit var accountService: AccountService

    @Test
    fun `get account balance when no transactions exist`() {
        val account = testDataFixture.getNewAccount()
        val accountValuation = accountService.getAccountValuation(account)
        assertEquals(BigDecimal.ZERO, accountValuation.totalValue())
    }

    @Test
    fun `get account balance after deposit and withdrawal`() {
        val account = testDataFixture.getNewAccount()
        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CreateDepositRequest(
                    accountNumber = account.accountNumber,
                    amount = BigDecimal("99.33"),
                    currency = "AUD"
                )
            )
            .`when`()
            .post("/transactions/deposits")
            .then()
            .statusCode(200)

        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CreateWithdrawalRequest(
                    accountNumber = account.accountNumber,
                    amount = BigDecimal("0.33"),
                    currency = "AUD"
                )
            )
            .`when`()
            .post("/transactions/withdrawals")
            .then()
            .statusCode(200)

        val accountValuation = accountService.getAccountValuation(account)
        assertEquals(BigDecimal("99.00"), accountValuation.totalValue())
    }
}