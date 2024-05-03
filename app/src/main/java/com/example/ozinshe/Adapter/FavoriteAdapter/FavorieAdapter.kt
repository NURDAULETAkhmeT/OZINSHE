package com.example.ozinshe.Adapter.FavoriteAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ozinshe.Data.FavoriteGET.FavoriteSpisokItem
import com.example.ozinshe.databinding.DizaineFavoriteRcViewBinding

@Suppress("DEPRECATION")
class FavorieAdapter(val mItemClickListener: ItemClickListener)  : RecyclerView.Adapter<FavorieAdapter.ViewHolder>() {
    private var listAdapter = mutableListOf<FavoriteSpisokItem>()

    interface ItemClickListener {
        fun onItemClick(id: Int)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(productList: List<FavoriteSpisokItem>) {
        listAdapter.clear()
        listAdapter.addAll(productList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: DizaineFavoriteRcViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(itemMovieMain: FavoriteSpisokItem) {
            // Предположим, что у CategoryMainMoviesItem есть метод для получения списка items,
            // и items содержит поле movies (вам может потребоваться адаптировать под вашу структуру)
            binding.favoriteRcViewGlavText.text = itemMovieMain.name
            binding.year.text = itemMovieMain.year.toString()
            binding.moviesType.text = itemMovieMain.movieType
            binding.series.text = "${itemMovieMain.seriesCount} серия"
            Glide.with(itemView.context).load(itemMovieMain.poster.link).into(binding.imageView5)
        }
        init {
            itemView.setOnClickListener {
                listAdapter[position].id.let { mItemClickListener.onItemClick(it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DizaineFavoriteRcViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listAdapter.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listAdapter[position])
    }
}
