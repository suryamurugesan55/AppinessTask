package com.surya.appinesstask.network

import com.surya.appinesstask.model.ProductResponse
import retrofit2.http.GET

interface ProductService {
    @GET("test/products.php")
    suspend fun getProducts(): ProductResponse
}