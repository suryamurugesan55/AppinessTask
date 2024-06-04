package com.surya.appinesstask.repository

import androidx.lifecycle.LiveData
import com.surya.appinesstask.helper.ProductDao
import com.surya.appinesstask.model.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(private val productDao: ProductDao) {

    suspend fun insertProducts(products: List<Product>) {
        productDao.insertAll(products)
    }

    suspend fun getProductById(productId: String): Product? {
        return productDao.getProductById(productId)
    }

    suspend fun getAllProducts(): List<Product> {
        return productDao.getAllProducts()
    }
}
