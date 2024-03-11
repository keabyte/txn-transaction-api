package com.keabyte.transaction_engine.transaction_api.service

import com.keabyte.transaction_engine.transaction_api.exception.BusinessException
import com.keabyte.transaction_engine.transaction_api.repository.AssetRepository
import com.keabyte.transaction_engine.transaction_api.repository.entity.AssetEntity
import com.keabyte.transaction_engine.transaction_api.type.AssetType
import com.keabyte.transaction_engine.transaction_api.web.model.asset.Asset
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class AssetService(private val assetRepository: AssetRepository) {

    @Transactional
    fun findByAssetCode(assetCode: String): AssetEntity {
        return assetRepository.findByAssetCode(assetCode)
            ?: throw BusinessException("No asset exists with asset code $assetCode")
    }

    @Transactional
    fun findCashAssetForCurrency(currency: String): AssetEntity {
        return assetRepository.findByTypeAndCurrency(AssetType.CASH, currency)
            .firstOrNull()
            ?: throw BusinessException("No cash asset exists for currency $currency")
    }

    @Transactional
    fun processAssetEvent(asset: Asset) {
        Log.info("Processing asset event: $asset")
        AssetEntity(
            assetCode = asset.assetCode,
            name = asset.name,
            type = asset.type,
            currency = asset.currency,
            roundingScale = asset.roundingScale,
            dividendYield = asset.dividendYield
        ).persist()
    }
}