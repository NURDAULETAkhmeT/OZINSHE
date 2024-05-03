package com.example.ozinshe.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ozinshe.Data.CategoryMainMovies.Movy
import com.example.ozinshe.databinding.DizaineHomeGlavnoeMoveBinding

class HomeCategoriTolukMult(val mItemClickListener: ItemClickListener) : RecyclerView.Adapter<HomeCategoriTolukMult.ViewHolder>() {
    private var listAdapter = mutableListOf<Movy>()

    interface ItemClickListener {
        fun onItemClick(id: Int)
    }


    fun submitList(productList: List<Movy>) {
        listAdapter.clear()
        listAdapter.addAll(productList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: DizaineHomeGlavnoeMoveBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(itemMovieMain: Movy) {
            // Предположим, что у CategoryMainMoviesItem есть метод для получения списка items,
            // и items содержит поле movies (вам может потребоваться адаптировать под вашу структуру)
                binding.homeGlavTextBond.text = itemMovieMain.name.toString()
                binding.homeGlavTextHent.text = itemMovieMain.description.toString()
                Glide.with(itemView.context).load(itemMovieMain.screenshots[0].link).into(binding.homeGlavImage)
        }
        init {
            itemView.setOnClickListener {
                listAdapter?.get(position)?.id?.let { it -> mItemClickListener.onItemClick(it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DizaineHomeGlavnoeMoveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listAdapter.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listAdapter[position])
    }
}
