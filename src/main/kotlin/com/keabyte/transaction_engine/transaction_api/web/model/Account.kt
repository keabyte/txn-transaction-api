package com.keabyte.transaction_engine.transaction_api.web.model

import java.time.OffsetDateTime

data class Account(val accountNumber: String, val clientNumber: String, val createdDate: OffsetDateTime)