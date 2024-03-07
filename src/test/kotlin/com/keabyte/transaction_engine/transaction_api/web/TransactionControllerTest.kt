package com.keabyte.transaction_engine.transaction_api.web

import com.keabyte.transaction_engine.transaction_api.web.fixture.AccountFixture
import com.keabyte.transaction_engine.transaction_api.web.model.CreateDepositRequest
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
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
                accountNumber = AccountFixture.defaultAccountNumber,
                amount = BigDecimal("100.33"),
                currency = "AUD"
            )
        )
    }
}