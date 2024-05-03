package com.example.ozinshe.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ozinshe.Data.Seasons.Video
import com.example.ozinshe.R
import com.example.ozinshe.databinding.DizaineSeriesRcViewSeriesBinding

class SeriesAdapter(val mItemClickListenerSeries: ItemClickListener) : RecyclerView.Adapter<SeriesAdapter.ViewHolder>() {
    private var listAdapter = mutableListOf<Video>()

    interface ItemClickListener {
        fun onItemClickSeries(link: String)
    }

    fun submitList(productList: List<Video>) {
        listAdapter.clear()
        listAdapter.addAll(productList)
        Log.d("video", listAdapter.toString())
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: DizaineSeriesRcViewSeriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(itemMovieMain: Video) {
            // Предположим, что у CategoryMainMoviesItem есть метод для получения списка items,
            // и items содержит поле movies (вам может потребоваться адаптировать под вашу структуру)
            binding.text.text = "${itemMovieMain.number} ${binding.root.context.getString(R.string.series_series)}"
            Glide.with(itemView.context).load("http://img.youtube.com/vi/${itemMovieMain.link}/maxresdefault.jpg").into(binding.shapeableImageView)
            // Вам может потребоваться использовать Glide для загрузки изображений, если есть такая необходимость
            // Glide.with(itemView.context).load(itemMovieMain.imageUrl).into(binding.imageView)
        }

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listAdapter[position].link?.let { it -> mItemClickListenerSeries.onItemClickSeries(it) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DizaineSeriesRcViewSeriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listAdapter.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listAdapter[position])
    }
}
