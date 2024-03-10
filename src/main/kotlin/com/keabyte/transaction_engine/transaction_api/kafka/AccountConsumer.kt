package com.keabyte.transaction_engine.transaction_api.kafka

import com.keabyte.transaction_engine.transaction_api.service.AccountService
import com.keabyte.transaction_engine.transaction_api.web.model.Account
import io.smallrye.reactive.messaging.annotations.Blocking
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.reactive.messaging.Incoming

@ApplicationScoped
class AccountConsumer(private val accountService: AccountService) {

    @Blocking
    @Incoming("accounts")
    fun consumeAccount(account: Account) {
        accountService.processAccountEvent(account)
    }
}