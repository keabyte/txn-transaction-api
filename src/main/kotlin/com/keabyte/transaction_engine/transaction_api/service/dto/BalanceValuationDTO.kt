package com.keabyte.transaction_engine.transaction_api.service.dto

import com.keabyte.transaction_engine.transaction_api.config.round
import com.keabyte.transaction_engine.transaction_api.repository.entity.BalanceEntity
import com.keabyte.transaction_engine.transaction_api.web.model.BalanceValuation
import java.math.BigDecimal

data class BalanceValuationDTO(val balance: BalanceEntity, val price: BigDecimal) {
    fun amount() = (balance.units * price).round(balance.asset.roundingScale)
    fun units() = balance.units
    fun toModel() = BalanceValuation(balance.units, price, amount())
}