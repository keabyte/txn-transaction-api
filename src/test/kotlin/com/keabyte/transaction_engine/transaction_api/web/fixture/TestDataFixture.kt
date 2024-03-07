package com.keabyte.transaction_engine.transaction_api.web.fixture

import com.keabyte.transaction_engine.transaction_api.entity.AccountEntity
import io.quarkus.runtime.Startup
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import java.time.OffsetDateTime

@ApplicationScoped
class TestDataFixture {

    @Startup
    fun init() {
        createTestAccount()
    }

    @Transactional
    fun createTestAccount() {
        AccountEntity(
            accountNumber = "P1000",
            dateCreated = OffsetDateTime.now(),
            clientNumber = "C1000"
        ).persist()
    }
}