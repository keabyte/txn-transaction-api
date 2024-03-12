package com.keabyte.transaction_engine.transaction_api.web.model

import com.keabyte.transaction_engine.transaction_api.type.AssetType
import java.math.BigDecimal

data class Asset(
    val assetCode: String,
    val name: String,
    val dividendYield: BigDecimal,
    val type: AssetType,
    val roundingScale: Int,
    val currency: String?,
)