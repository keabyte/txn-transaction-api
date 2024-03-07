package com.keabyte.transaction_engine.transaction_api.repository.entity

import com.keabyte.transaction_engine.transaction_api.type.TransactionType
import com.keabyte.transaction_engine.transaction_api.web.model.TransactionEvent
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.SourceType
import java.time.OffsetDateTime
import java.util.*

@Entity(name = "transaction_event")
data class TransactionEventEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    val transactionReference: String = UUID.randomUUID().toString(),
    @CreationTimestamp(source = SourceType.DB)
    val dateCreated: OffsetDateTime? = null,
    @Enumerated(EnumType.STRING)
    val type: TransactionType,
    @OneToMany(mappedBy = "transactionEvent", cascade = [CascadeType.ALL])
    var accountTransactions: MutableList<AccountTransactionEntity> = ArrayList()
) : PanacheEntityBase() {

    fun toModel() = TransactionEvent(
        transactionReference = transactionReference,
        dateCreated = dateCreated!!,
        type = type,
        accountTransactions = accountTransactions.map { it.toModel() }
    )
}