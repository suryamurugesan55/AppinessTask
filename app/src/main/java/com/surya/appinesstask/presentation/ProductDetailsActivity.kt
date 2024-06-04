package com.surya.appinesstask.presentation

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.surya.appinesstask.R
import com.surya.appinesstask.adapter.AvailableLanguageAdapter
import com.surya.appinesstask.databinding.ActivityProductDetailsBinding
import com.surya.appinesstask.model.Product
import com.surya.appinesstask.utils.Const
import com.surya.appinesstask.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding

    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupToolbar()
        setupLoadData()
        setupViewModelObserver()
    }

    private fun setupLoadData() {
        val bundle = intent.extras
        if (bundle != null) {
            val id = bundle.getString("product_id")
            productViewModel.getProductById(id.toString())
        } else {
            finish()
        }
    }

    private fun setupViewModelObserver() {
        productViewModel.productByIdResultLiveData.observe(this) { result ->
            setDataToUi(result)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setDataToUi(product: Product?) {
        val circularProgressDrawable = CircularProgressDrawable(this).apply {
            strokeWidth = 5f
            centerRadius = 30f
            setColorSchemeColors(Color.BLUE)
            start()
        }

        val languageAdapter = AvailableLanguageAdapter(product?.availableLanguages!!)

        val price = product.price
        val discount = product.discount
        val discountPrice = price - discount


        binding.apply {
            tvName.text = product.name
            tvVedic.text = product.vedic
            tvRate.text = product.avg.toString()
            tvPages.text = product.pagesintext
            tvSoldCount.text = product.soldcount +" "+ Const.BOOKS_SOLD
            tvDescription.text = product.description
            tvAmount.text = "₹$discountPrice"
            tvFullAmount.text = "₹$price"
            tvFullAmount.paintFlags = tvFullAmount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            Glide.with(this@ProductDetailsActivity)
                .load(product.imagePath.wide)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontTransform()
                .placeholder(circularProgressDrawable)
                .error(R.drawable.ic_launcher_background)
                .into(ivBook)

            recyclerview.adapter = languageAdapter
            recyclerview.layoutManager = GridLayoutManager(this@ProductDetailsActivity, 3)
        }
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(Const.TRUE)
            setDisplayShowTitleEnabled(Const.FALSE)
            setHomeAsUpIndicator(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        }

        // Optional: Handle toolbar navigation icon click
        toolbar.setNavigationOnClickListener {
            // Handle the navigation icon click event (e.g., navigate back)
            finish()
        }
    }
}