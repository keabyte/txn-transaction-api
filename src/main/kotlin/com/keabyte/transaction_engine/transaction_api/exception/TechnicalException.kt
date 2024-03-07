package com.keabyte.transaction_engine.client_api.exception

class TechnicalException(cause: Throwable, message: String) : RuntimeException(message, cause)