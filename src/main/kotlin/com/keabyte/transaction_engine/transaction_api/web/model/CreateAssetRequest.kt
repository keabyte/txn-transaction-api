package com.keabyte.transaction_engine.transaction_api.web.model

import com.keabyte.transaction_engine.transaction_api.repository.entity.AssetEntity
import com.keabyte.transaction_engine.transaction_api.type.AssetType
import java.math.BigDecimal
import java.time.OffsetDateTime

data class CreateAssetRequest(
    val assetCode: String,
    val name: String,
    val foundedDate: OffsetDateTime,
    val dividendYield: BigDecimal,
    val description: String,
    val websiteUrl: String?,
    val type: AssetType,
    val roundingScale: Int,
    val currency: String?
) {
    fun toEntity() = AssetEntity(
        assetCode = assetCode,
        name = name,
        foundedDate = foundedDate,
        dividendYield = dividendYield,
        description = description,
        websiteUrl = websiteUrl,
        type = type,
        roundingScale = roundingScale,
        currency = currency
    )
}
