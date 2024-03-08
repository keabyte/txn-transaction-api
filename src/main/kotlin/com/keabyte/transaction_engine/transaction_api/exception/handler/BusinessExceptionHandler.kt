package com.keabyte.transaction_engine.transaction_api.exception.handler

import com.keabyte.transaction_engine.transaction_api.exception.BusinessException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class BusinessExceptionHandler : ExceptionMapper<BusinessException> {
    override fun toResponse(exception: BusinessException): Response {
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.message).build()
    }
}