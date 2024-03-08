package com.keabyte.transaction_engine.transaction_api.repository

import com.keabyte.transaction_engine.transaction_api.repository.entity.PriceEntity
import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class PriceRepository : PanacheRepository<PriceEntity> {

    fun findLatestPriceForAsset(assetCode: String): PriceEntity? {
        return find("asset.assetCode = ?1 order by effectiveDate DESC", assetCode).firstResult()
    }
}