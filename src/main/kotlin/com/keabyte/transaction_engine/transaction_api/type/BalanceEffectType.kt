package com.keabyte.transaction_engine.transaction_api.type

enum class BalanceEffectType {
    CREDIT,
    DEBIT,
    NONE;

    companion object {
        fun toBalanceEffectMultiplier(BalanceEffectType: BalanceEffectType): Int = when (BalanceEffectType) {
            CREDIT -> 1
            DEBIT -> -1
            NONE -> 0
        }
    }
}