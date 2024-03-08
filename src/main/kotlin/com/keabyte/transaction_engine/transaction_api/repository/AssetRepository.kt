package com.keabyte.transaction_engine.transaction_api.repository

import com.keabyte.transaction_engine.transaction_api.repository.entity.AssetEntity
import com.keabyte.transaction_engine.transaction_api.type.AssetType
import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class AssetRepository : PanacheRepository<AssetEntity> {

    fun findByAssetCode(assetCode: String): AssetEntity? =
        find("assetCode", assetCode).firstResult()

    fun findByTypeAndCurrency(type: AssetType, currency: String): MutableList<AssetEntity> =
        find("type = ?1 and currency = ?2", type, currency).list()
}