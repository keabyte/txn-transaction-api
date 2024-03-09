package com.keabyte.transaction_engine.transaction_api.web

import com.keabyte.transaction_engine.transaction_api.fixture.TestDataFixture
import com.keabyte.transaction_engine.transaction_api.web.model.transaction.CreateDepositRequest
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import jakarta.inject.Inject
import jakarta.ws.rs.core.MediaType
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import java.math.BigDecimal

@QuarkusTest
class AccountControllerTest {

    @Inject
    lateinit var testDataFixture: TestDataFixture

    @Inject
    lateinit var accountController: AccountController

    @Test
    fun `get account valuation when no transactions exist`() {
        val accountValuation = accountController.getAccountValuation(TestDataFixture.zeroBalanceAccountNumber)
        assertThat(accountValuation.totalValue).isEqualTo(BigDecimal.ZERO)
    }

    @Test
    fun `get account valuation after a deposit`() {
        val account = testDataFixture.getNewAccount()

        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CreateDepositRequest(
                    accountNumber = account.accountNumber,
                    amount = BigDecimal("10000.21"),
                    currency = "AUD"
                )
            )
            .`when`()
            .post("/transactions/deposits")
            .then()
            .statusCode(200)

        val accountValuation = accountController.getAccountValuation(account.accountNumber)
        assertThat(accountValuation.totalValue).isEqualTo(BigDecimal("10000.21"))
    }

    @Test
    fun `get account valuation rest call`() {
        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON)
            .`when`()
            .get("/accounts/${TestDataFixture.zeroBalanceAccountNumber}/valuation")
            .then()
            .statusCode(200)
            .body("totalValue", equalTo(0))
    }
}