package com.surya.appinesstask.viewmodel

import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surya.appinesstask.model.Product
import com.surya.appinesstask.network.ProductService
import com.surya.appinesstask.repository.ProductRepository
import com.surya.appinesstask.utils.Const
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val productService: ProductService,
    private val editor: SharedPreferences.Editor
) : ViewModel() {

    // loader
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    // get products from api
    fun fetchAndStoreProducts() {
        _isLoading.value = Const.TRUE
        viewModelScope.launch {
            try {
                val response = productService.getProducts()
                val products = response.products.map { entry ->
                    entry.value.copy(id = entry.key)
                }
                repository.insertProducts(products)
                // products loaded success so first time will goes to false
                editor.putBoolean(Const.FIRST_TIME_ENTRY, Const.TRUE)
                editor.apply()
                // call products from db
                getAllProducts()
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            } finally {
                _isLoading.value = Const.FALSE
            }
        }
    }

    // products from local db
    private val _productResultLiveData = MutableLiveData<List<Product>>()
    val productResultLiveData: LiveData<List<Product>> get() = _productResultLiveData

    fun getAllProducts() {
        viewModelScope.launch {
            _productResultLiveData.postValue(repository.getAllProducts())
        }
    }

    // product by id from local db
    private val _productByIdResultLiveData = MutableLiveData<Product>()
    val productByIdResultLiveData: LiveData<Product> get() = _productByIdResultLiveData

    fun getProductById(productId: String) {
        viewModelScope.launch {
            _productByIdResultLiveData.postValue(repository.getProductById(productId))
        }
    }
}
