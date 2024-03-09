package com.keabyte.transaction_engine.transaction_api.repository.entity

import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.persistence.*
import java.math.BigDecimal

@Entity(name = "balance")
data class BalanceEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "account_id")
    val account: AccountEntity,
    @ManyToOne
    @JoinColumn(name = "asset_id")
    val asset: AssetEntity,
    var units: BigDecimal,
) : PanacheEntityBase()