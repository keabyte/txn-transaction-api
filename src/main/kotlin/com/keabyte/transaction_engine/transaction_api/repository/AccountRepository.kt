package com.keabyte.transaction_engine.transaction_api.repository

import com.keabyte.transaction_engine.transaction_api.entity.AccountEntity
import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class AccountRepository : PanacheRepository<AccountEntity> {

    fun findByAccountNumber(accountNumber: String) =
        find("accountNumber", accountNumber).firstResult<AccountEntity>()
}