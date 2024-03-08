package com.keabyte.transaction_engine.transaction_api.web

import com.keabyte.transaction_engine.transaction_api.service.PriceService
import com.keabyte.transaction_engine.transaction_api.web.model.CreatePriceRequest
import com.keabyte.transaction_engine.transaction_api.web.model.Price
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path

@Path("/assets/{assetCode}/prices")
class PriceController(private val priceService: PriceService) {

    @Path("/{assetCode}/prices")
    @POST
    fun createPrice(assetCode: String, request: CreatePriceRequest): Price {
        return priceService.createPrice(request.withAssetCode(assetCode)).toModel()
    }
}
