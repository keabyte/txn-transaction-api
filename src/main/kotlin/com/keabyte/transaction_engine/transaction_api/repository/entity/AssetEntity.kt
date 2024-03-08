package com.keabyte.transaction_engine.transaction_api.repository.entity

import com.keabyte.transaction_engine.transaction_api.type.AssetType
import com.keabyte.transaction_engine.transaction_api.web.model.Asset
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.SourceType
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity(name = "asset")
data class AssetEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    val assetCode: String,
    val name: String,
    @CreationTimestamp(source = SourceType.DB)
    val createdDate: OffsetDateTime? = null,
    val foundedDate: OffsetDateTime? = null,
    val dividendYield: BigDecimal,
    val description: String,
    val websiteUrl: String? = null,
    @Enumerated(EnumType.STRING)
    val type: AssetType,
    val roundingScale: Int,
    val currency: String?
) :
    PanacheEntityBase() {
    fun toModel() = Asset(
        assetCode = assetCode,
        name = name,
        createdDate = createdDate!!,
        foundedDate = foundedDate!!,
        dividendYield = dividendYield,
        description = description,
        websiteUrl = websiteUrl,
        type = type,
        roundingScale = roundingScale,
        currency = currency
    )
}