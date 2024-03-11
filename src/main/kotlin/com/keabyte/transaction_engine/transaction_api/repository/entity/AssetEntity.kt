package com.keabyte.transaction_engine.transaction_api.repository.entity

import com.keabyte.transaction_engine.transaction_api.type.AssetType
import com.keabyte.transaction_engine.transaction_api.web.model.asset.Asset
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.persistence.*
import java.math.BigDecimal

@Entity(name = "asset")
data class AssetEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    val assetCode: String,
    val name: String,
    val dividendYield: BigDecimal,
    @Enumerated(EnumType.STRING)
    val type: AssetType,
    val roundingScale: Int,
    val currency: String?,
    val latestPrice: BigDecimal
) :
    PanacheEntityBase() {
    fun toModel() = Asset(
        assetCode = assetCode,
        name = name,
        dividendYield = dividendYield,
        type = type,
        roundingScale = roundingScale,
        currency = currency,
        latestPrice = latestPrice
    )
}