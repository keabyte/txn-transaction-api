package com.keabyte.transaction_engine.transaction_api.web.model

import java.math.BigDecimal
import java.time.OffsetDateTime

data class CreatePriceRequest(
    val effectiveDate: OffsetDateTime,
    val price: BigDecimal,
    val currency: String,
    val assetCode: String = ""
) {
    fun withAssetCode(assetCode: String) = CreatePriceRequest(
        effectiveDate = effectiveDate,
        price = price,
        currency = currency,
        assetCode = assetCode
    )
}