package com.example.ozinshe.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ozinshe.Data.IDMovies.Screenshot
import com.example.ozinshe.databinding.DizaineDetailScreenshotsBinding

class ScreenshotsAdapter(val mItemClickListener: ItemClickListener)   : RecyclerView.Adapter<ScreenshotsAdapter.ViewHolder>() {
    private var listAdapter = mutableListOf<Screenshot>()

    interface ItemClickListener {
        fun onItemClick(position: Int)
    }

    fun submitList(productList: List<Screenshot>) {
        listAdapter.clear()
        listAdapter.addAll(productList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: DizaineDetailScreenshotsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(itemMovieMain: Screenshot) {
            // Предположим, что у CategoryMainMoviesItem есть метод для получения списка items,
            // и items содержит поле movies (вам может потребоваться адаптировать под вашу структуру)
            Glide.with(itemView.context).load(itemMovieMain.link).into(binding.detailImage)
        }
        init {
            itemView.setOnClickListener {
                listAdapter?.get(position)?.id?.let { it -> mItemClickListener.onItemClick(it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DizaineDetailScreenshotsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listAdapter.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listAdapter[position])
    }
}
