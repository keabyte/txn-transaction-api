package com.keabyte.transaction_engine.transaction_api.repository

import com.keabyte.transaction_engine.transaction_api.repository.entity.BalanceEntity
import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class BalanceRepository : PanacheRepository<BalanceEntity> {

    fun findByAccountId(accountId: Long): List<BalanceEntity> {
        return list("account.id", accountId)
    }
}