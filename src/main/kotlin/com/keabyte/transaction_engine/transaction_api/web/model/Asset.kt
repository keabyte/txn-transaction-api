package com.keabyte.transaction_engine.transaction_api.web.model

import java.math.BigDecimal
import java.time.OffsetDateTime

data class Asset(
    val assetCode: String,
    val createdDate: OffsetDateTime,
    val foundedDate: OffsetDateTime,
    val dividendYield: BigDecimal,
    val description: String,
    val websiteUrl: String?
)