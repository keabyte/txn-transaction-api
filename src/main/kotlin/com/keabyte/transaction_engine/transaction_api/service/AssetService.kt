package com.keabyte.transaction_engine.transaction_api.service

import com.keabyte.transaction_engine.transaction_api.exception.BusinessException
import com.keabyte.transaction_engine.transaction_api.repository.AssetRepository
import com.keabyte.transaction_engine.transaction_api.repository.entity.AssetEntity
import com.keabyte.transaction_engine.transaction_api.type.AssetType
import com.keabyte.transaction_engine.transaction_api.web.model.asset.CreateAssetRequest
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
    fun createAsset(request: CreateAssetRequest): AssetEntity {
        val assetEntity = request.toEntity()
        assetEntity.persist()
        return assetEntity
    }

    @Transactional
    fun findCashAssetForCurrency(currency: String): AssetEntity {
        return assetRepository.findByTypeAndCurrency(AssetType.CASH, currency)
            .firstOrNull()
            ?: throw BusinessException("No cash asset exists for currency $currency")
    }
}