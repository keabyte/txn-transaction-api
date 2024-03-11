package com.keabyte.transaction_engine.transaction_api.kafka

import com.keabyte.transaction_engine.transaction_api.service.AssetService
import com.keabyte.transaction_engine.transaction_api.web.model.asset.Asset
import io.smallrye.reactive.messaging.annotations.Blocking
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.reactive.messaging.Incoming

@ApplicationScoped
class AssetConsumer(private val assetService: AssetService) {

    @Blocking
    @Incoming("assets")
    fun consumeAsset(asset: Asset) {
        assetService.processAssetEvent(asset)
    }
}