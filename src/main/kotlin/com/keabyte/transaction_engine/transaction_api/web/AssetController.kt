package com.keabyte.transaction_engine.transaction_api.web

import com.keabyte.transaction_engine.transaction_api.service.AssetService
import com.keabyte.transaction_engine.transaction_api.web.model.Asset
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/assets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AssetController(private val assetService: AssetService) {

    fun findByAssetCode(assetCode: String): Asset {
        return assetService.findByAssetCode(assetCode).toModel()
    }
}