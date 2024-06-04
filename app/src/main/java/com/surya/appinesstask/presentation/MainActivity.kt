package com.surya.appinesstask.presentation

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.surya.appinesstask.R
import com.surya.appinesstask.adapter.AllProductsAdapter
import com.surya.appinesstask.databinding.ActivityMainBinding
import com.surya.appinesstask.model.Product
import com.surya.appinesstask.utils.Const
import com.surya.appinesstask.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AllProductsAdapter.OnItemClick {
    private lateinit var binding: ActivityMainBinding

    private val productViewModel: ProductViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var productListAdapter: AllProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupAdapter()
        setupViewModelObserver()
        setupDataLoaded()
    }

    private fun setupAdapter() {
        productListAdapter = AllProductsAdapter(this, this@MainActivity)
        binding.recyclerview.apply {
            adapter = productListAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupViewModelObserver() {
        productViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressCircular.visibility = View.VISIBLE
            } else {
                binding.progressCircular.visibility = View.GONE
            }
        }

        productViewModel.productResultLiveData.observe(this) { result ->
            if(result.isNotEmpty()) {
                productListAdapter.setLiveData(result)
            }
        }
    }

    private fun setupDataLoaded() {
        if (sharedPreferences.getBoolean(Const.FIRST_TIME_ENTRY, Const.FALSE) == Const.FALSE) {
            productViewModel.fetchAndStoreProducts()
        } else {
            productViewModel.getAllProducts()
        }
    }

    override fun onItemClicked(product: Product) {
        val bundle = Bundle()
        bundle.putString("product_id", product.id);
        val intent = Intent(this@MainActivity, ProductDetailsActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}