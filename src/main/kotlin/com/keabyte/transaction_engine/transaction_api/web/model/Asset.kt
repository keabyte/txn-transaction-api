package com.keabyte.transaction_engine.transaction_api.web.model

import com.keabyte.transaction_engine.transaction_api.type.AssetType
import java.math.BigDecimal
import java.time.OffsetDateTime

data class Asset(
    val assetCode: String,
    val name: String,
    val createdDate: OffsetDateTime,
    val foundedDate: OffsetDateTime,
    val dividendYield: BigDecimal,
    val description: String,
    val websiteUrl: String?,
    val type: AssetType,
    val roundingScale: Int,
    val currency: String?
)