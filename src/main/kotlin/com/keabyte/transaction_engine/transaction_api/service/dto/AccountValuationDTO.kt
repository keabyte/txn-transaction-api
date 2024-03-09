package com.keabyte.transaction_engine.transaction_api.service.dto

import com.keabyte.transaction_engine.transaction_api.exception.BusinessException
import com.keabyte.transaction_engine.transaction_api.web.model.AccountValuation
import java.math.BigDecimal

data class AccountValuationDTO(val balances: MutableList<BalanceValuationDTO> = arrayListOf()) {

    /**
     * Return the total value of the account
     */
    fun totalValue(): BigDecimal {
        return balances.sumOf { it.amount }
    }

    /**
     * Return the unit holdings of a single asset
     */
    fun getAssetUnits(assetCode: String): BigDecimal {
        return balances.first { it.balance.asset.assetCode == assetCode }.units()
    }

    /**
     * Return the value of the asset using the latest price
     */
    fun getAssetValue(assetCode: String): BigDecimal {
        return balances.firstOrNull { it.balance.asset.assetCode == assetCode }?.amount ?: BigDecimal.ZERO
    }

    fun adjustAssetUnits(assetCode: String, units: BigDecimal) {
        val balance = balances.firstOrNull { it.balance.asset.assetCode == assetCode }
        if (balance != null) {
            balance.balance.units += units
        } else {
            throw BusinessException("No balance exists for asset $assetCode")
        }
    }

    fun hasAssetBalance(assetCode: String): Boolean {
        return balances.any { it.balance.asset.assetCode == assetCode }
    }

    fun toModel() = AccountValuation(totalValue(), balances.map { it.toModel() })
}



