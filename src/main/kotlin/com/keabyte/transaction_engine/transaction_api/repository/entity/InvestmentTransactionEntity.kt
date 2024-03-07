package com.keabyte.transaction_engine.transaction_api.repository.entity

import com.keabyte.transaction_engine.transaction_api.type.BalanceEffectType
import com.keabyte.transaction_engine.transaction_api.web.model.InvestmentTransaction
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.persistence.*
import java.math.BigDecimal

@Entity(name = "investment_transaction")
data class InvestmentTransactionEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "account_transaction_id")
    val accountTransaction: AccountTransactionEntity,
    val amount: BigDecimal,
    val currency: String,
    @Enumerated(EnumType.STRING)
    val balanceEffectType: BalanceEffectType
) : PanacheEntityBase() {
    fun toModel() = InvestmentTransaction(amount = amount, currency = currency, balanceEffectType = balanceEffectType)
}