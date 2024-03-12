package com.keabyte.transaction_engine.transaction_api.kafka

import com.keabyte.transaction_engine.transaction_api.service.AssetService
import com.keabyte.transaction_engine.transaction_api.web.model.Price
import io.smallrye.reactive.messaging.annotations.Blocking
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.reactive.messaging.Incoming

@ApplicationScoped
class PriceConsumer(private val assetService: AssetService) {

    @Blocking
    @Incoming("prices")
    fun consume(price: Price) {
        assetService.processPriceEvent(price)
    }
}