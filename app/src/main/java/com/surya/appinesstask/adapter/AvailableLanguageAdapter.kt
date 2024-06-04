package com.surya.appinesstask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.surya.appinesstask.databinding.LytAvailableLanguageBinding

class AvailableLanguageAdapter(
    private var languageList: List<String>
) : RecyclerView.Adapter<AvailableLanguageAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: LytAvailableLanguageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(language: String) {

            binding.apply {
                tvLanguage.text = language
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LytAvailableLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = languageList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(languageList[position])
    }

}