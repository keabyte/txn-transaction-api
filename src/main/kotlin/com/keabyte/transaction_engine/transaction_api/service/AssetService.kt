package com.keabyte.transaction_engine.transaction_api.service

import com.keabyte.transaction_engine.client_api.exception.BusinessException
import com.keabyte.transaction_engine.transaction_api.repository.AssetRepository
import com.keabyte.transaction_engine.transaction_api.repository.entity.AssetEntity
import com.keabyte.transaction_engine.transaction_api.web.model.CreateAssetRequest
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
        val assetEntity = AssetEntity(
            assetCode = request.assetCode,
            name = request.name,
            foundedDate = request.foundedDate,
            dividendYield = request.dividendYield,
            description = request.description,
            websiteUrl = request.websiteUrl
        )
        assetEntity.persist()
        return assetEntity
    }
}