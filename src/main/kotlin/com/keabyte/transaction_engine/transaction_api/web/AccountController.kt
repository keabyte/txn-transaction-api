package com.keabyte.transaction_engine.transaction_api.web

import com.keabyte.transaction_engine.transaction_api.service.AccountService
import com.keabyte.transaction_engine.transaction_api.web.model.AccountValuation
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path

@Path("/accounts/{accountNumber}")
class AccountController(private val accountService: AccountService) {

    @Path("/valuation")
    @GET
    fun getAccountValuation(accountNumber: String): AccountValuation {
        return accountService.getAccountValuation(accountNumber).toModel()
    }
}