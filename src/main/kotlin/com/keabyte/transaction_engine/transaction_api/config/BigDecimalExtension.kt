package com.keabyte.transaction_engine.transaction_api.config

import java.math.BigDecimal

class BigDecimalExtension

/** Rounds a BigDecimal to a given scale */
fun BigDecimal.round(scale: Int): BigDecimal {
    return this.setScale(scale, BigDecimal.ROUND_HALF_UP)
}