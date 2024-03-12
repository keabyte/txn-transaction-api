package com.keabyte.transaction_engine.transaction_api.web.model

import java.math.BigDecimal

data class Price(
    val effectiveDate: String,
    val price: BigDecimal,
    val currency: String,
    val assetCode: String
)