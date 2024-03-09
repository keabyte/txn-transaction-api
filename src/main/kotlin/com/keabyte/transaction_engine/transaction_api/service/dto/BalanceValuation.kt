package com.keabyte.transaction_engine.transaction_api.service.dto

import com.keabyte.transaction_engine.transaction_api.config.round
import com.keabyte.transaction_engine.transaction_api.repository.entity.BalanceEntity
import com.keabyte.transaction_engine.transaction_api.repository.entity.PriceEntity

data class BalanceValuation(val balance: BalanceEntity, val price: PriceEntity) {
    val amount = (balance.units * price.price).round(balance.asset.roundingScale)

    fun units() = balance.units

    fun price() = price.price
}