package com.keabyte.transaction_engine.transaction_api.service

import com.keabyte.transaction_engine.transaction_api.repository.entity.PriceEntity
import com.keabyte.transaction_engine.transaction_api.web.model.CreatePriceRequest
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class PriceService(private val assetService: AssetService) {

    @Transactional
    fun createPrice(request: CreatePriceRequest): PriceEntity {
        val asset = assetService.findByAssetCode(request.assetCode)
        val price = PriceEntity(
            asset = asset,
            effectiveDate = request.effectiveDate,
            price = request.price,
            currency = request.currency
        )
        price.persist()
        return price
    }
}