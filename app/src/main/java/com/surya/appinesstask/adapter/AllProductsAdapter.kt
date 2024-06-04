package com.surya.appinesstask.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.surya.appinesstask.R
import com.surya.appinesstask.databinding.LytProductListBinding
import com.surya.appinesstask.model.Product

class AllProductsAdapter(
    private val context: Context,
    private val onItemClick: OnItemClick,
) : RecyclerView.Adapter<AllProductsAdapter.ViewHolder>() {
    private var productList: List<Product> = emptyList()

    interface OnItemClick {
        fun onItemClicked(product: Product)
    }

    inner class ViewHolder(private val binding: LytProductListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val circularProgressDrawable = CircularProgressDrawable(context).apply {
            strokeWidth = 5f
            centerRadius = 30f
            setColorSchemeColors(Color.BLUE)
            start()
        }

        fun bind(product: Product) {

            binding.apply {
                tvName.text = product.name
                tvVedic.text = product.vedic
                tvRate.text = product.avg.toString()

                Glide.with(context)
                    .load(product.imagePath.square)
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontTransform()
                    .placeholder(circularProgressDrawable)
                    .error(R.drawable.ic_launcher_background)
                    .into(ivBook)
            }

            itemView.setOnClickListener {
                onItemClick.onItemClicked(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LytProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setLiveData(liveProductList: List<Product>) {
        this.productList = liveProductList
        notifyDataSetChanged()
    }

}