package com.keabyte.transaction_engine.transaction_api.service

import com.keabyte.transaction_engine.client_api.exception.BusinessException
import com.keabyte.transaction_engine.transaction_api.repository.PriceRepository
import com.keabyte.transaction_engine.transaction_api.repository.entity.PriceEntity
import com.keabyte.transaction_engine.transaction_api.web.model.CreatePriceRequest
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class PriceService(private val assetService: AssetService, private val priceRepository: PriceRepository) {

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

    @Transactional
    fun getLatestPriceForAsset(assetCode: String): PriceEntity {
        return priceRepository.findLatestPriceForAsset(assetCode)
            ?: throw BusinessException("No price exists for asset with code $assetCode")
    }
}