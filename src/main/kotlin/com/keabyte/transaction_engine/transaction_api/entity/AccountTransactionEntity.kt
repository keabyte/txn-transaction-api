package com.keabyte.transaction_engine.transaction_api.entity

import com.keabyte.transaction_engine.transaction_api.web.model.AccountTransaction
import io.quarkus.hibernate.orm.panache.PanacheEntity
import jakarta.persistence.*

@Entity(name = "account_transaction")
data class AccountTransactionEntity(
    @ManyToOne
    @JoinColumn(name = "transaction_event_id")
    val transactionEvent: TransactionEventEntity,
    val accountNumber: String,
    @OneToMany(mappedBy = "accountTransaction", cascade = [CascadeType.ALL])
    var investmentTransactions: MutableList<InvestmentTransactionEntity> = ArrayList()
) : PanacheEntity() {

    fun toModel() = AccountTransaction(
        accountNumber = accountNumber,
        invesmentTransactions = investmentTransactions.map { it.toModel() })
}