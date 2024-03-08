package com.keabyte.transaction_engine.transaction_api.web

import com.keabyte.transaction_engine.transaction_api.service.AssetService
import com.keabyte.transaction_engine.transaction_api.web.model.asset.Asset
import com.keabyte.transaction_engine.transaction_api.web.model.asset.CreateAssetRequest
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/assets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AssetController(private val assetService: AssetService) {

    @Path("/{assetCode}")
    @GET
    fun findByAssetCode(assetCode: String): Asset {
        return assetService.findByAssetCode(assetCode).toModel()
    }

    @POST
    fun createAsset(request: CreateAssetRequest): Asset {
        return assetService.createAsset(request).toModel()
    }
}