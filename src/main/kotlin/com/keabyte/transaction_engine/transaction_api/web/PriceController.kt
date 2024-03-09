package com.keabyte.transaction_engine.transaction_api.web

import com.keabyte.transaction_engine.transaction_api.service.PriceService
import com.keabyte.transaction_engine.transaction_api.web.model.asset.Price
import com.keabyte.transaction_engine.transaction_api.web.model.transaction.CreatePriceRequest
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Tag(name = "Prices")
@Path("/assets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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
