package com.keabyte.transaction_engine.transaction_api.exception

class TechnicalException(cause: Throwable, message: String) : RuntimeException(message, cause)