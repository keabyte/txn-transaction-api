package com.keabyte.transaction_engine.transaction_api.web.model

import java.math.BigDecimal
import java.time.OffsetDateTime

data class CreateAssetRequest(
    val assetCode: String,
    val name: String,
    val foundedDate: OffsetDateTime,
    val dividendYield: BigDecimal,
    val description: String,
    val websiteUrl: String?
)
