package com.keabyte.transaction_engine.transaction_api.repository

import com.keabyte.transaction_engine.transaction_api.repository.entity.AssetEntity
import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class AssetRepository : PanacheRepository<AssetEntity> {

    fun findByAssetCode(assetCode: String) =
        find("assetCode", assetCode).firstResult<AssetEntity>()
}