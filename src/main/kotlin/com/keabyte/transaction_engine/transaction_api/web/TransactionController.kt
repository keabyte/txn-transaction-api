package com.keabyte.transaction_engine.transaction_api.web

import com.keabyte.transaction_engine.transaction_api.service.TransactionService
import com.keabyte.transaction_engine.transaction_api.web.model.transaction.CreateDepositRequest
import com.keabyte.transaction_engine.transaction_api.web.model.transaction.CreateWithdrawalRequest
import com.keabyte.transaction_engine.transaction_api.web.model.transaction.TransactionEvent
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Tag(name = "Transactions")
@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class TransactionController(private val transactionService: TransactionService) {

    @POST
    @Path("/deposits")
    fun createDeposit(request: CreateDepositRequest): TransactionEvent {
        return transactionService.createDeposit(request).toModel()
    }

    @POST
    @Path("/withdrawals")
    fun createWithdrawal(request: CreateWithdrawalRequest): TransactionEvent {
        return transactionService.createWithdrawal(request).toModel()
    }
}