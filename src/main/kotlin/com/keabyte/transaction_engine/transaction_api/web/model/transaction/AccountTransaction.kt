package com.keabyte.transaction_engine.transaction_api.web.model.transaction

data class AccountTransaction(val accountNumber: String, val invesmentTransactions: List<InvestmentTransaction>)