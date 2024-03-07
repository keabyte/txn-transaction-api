package com.keabyte.transaction_engine.transaction_api.service

import com.keabyte.transaction_engine.client_api.exception.BusinessException
import com.keabyte.transaction_engine.transaction_api.repository.AssetRepository
import com.keabyte.transaction_engine.transaction_api.repository.entity.AssetEntity
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class AssetService(private val assetRepository: AssetRepository) {

    @Transactional
    fun findByAssetCode(assetCode: String): AssetEntity {
        return assetRepository.findByAssetCode(assetCode)
            ?: throw BusinessException("No asset exists with asset code $assetCode")
    }
}