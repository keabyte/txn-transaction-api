package com.keabyte.transaction_engine.transaction_api

import jakarta.ws.rs.core.Application
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition
import org.eclipse.microprofile.openapi.annotations.info.Info


@OpenAPIDefinition(
    info = Info(
        title = "txn-transaction-api",
        version = "0.0.1",
    )
)
class Application : Application()