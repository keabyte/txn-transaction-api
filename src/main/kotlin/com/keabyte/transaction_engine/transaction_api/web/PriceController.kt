package com.keabyte.transaction_engine.transaction_api.web

import com.keabyte.transaction_engine.transaction_api.service.PriceService
import com.keabyte.transaction_engine.transaction_api.web.model.transaction.CreatePriceRequest
import com.keabyte.transaction_engine.transaction_api.web.model.asset.Price
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path

@Path("/assets/{assetCode}/prices")
class PriceController(private val priceService: PriceService) {

    @Path("/{assetCode}/prices")
    @POST
    fun createPrice(assetCode: String, request: CreatePriceRequest): Price {
        return priceService.createPrice(request.withAssetCode(assetCode)).toModel()
    }

    @Path("/{assetCode}/prices/latest")
    @GET
    fun getLatestPrice(assetCode: String): Price {
        return priceService.getLatestPriceForAsset(assetCode).toModel()
    }
}
