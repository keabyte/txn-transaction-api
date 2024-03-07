package com.keabyte.transaction_engine.transaction_api.entity

import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.SourceType
import java.time.OffsetDateTime
import java.util.*

@Entity(name = "account")
data class AccountEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    val accountNumber: String = UUID.randomUUID().toString(),
    @CreationTimestamp(source = SourceType.DB)
    val dateCreated: OffsetDateTime? = null,
    val clientNumber: String
) : PanacheEntityBase()