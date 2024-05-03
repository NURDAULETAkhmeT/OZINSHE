import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ozinshe.Data.CategoryAge.CategoryAgeItem
import com.example.ozinshe.databinding.DizaineHomeJanrBinding

class HomeCategoryAge : ListAdapter<CategoryAgeItem, HomeCategoryAge.ViewHolderCategoryAge>(YourDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCategoryAge {
        val binding = DizaineHomeJanrBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderCategoryAge(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderCategoryAge, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolderCategoryAge(private val binding: DizaineHomeJanrBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(itemMovieMain: CategoryAgeItem) {
            binding.homeJanrText.text = itemMovieMain.name
            Glide.with(itemView.context).load(itemMovieMain.link).into(binding.homeJanrImage)
        }
    }

    private class YourDiffCallback : DiffUtil.ItemCallback<CategoryAgeItem>() {
        override fun areItemsTheSame(oldItem: CategoryAgeItem, newItem: CategoryAgeItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoryAgeItem, newItem: CategoryAgeItem): Boolean {
            return oldItem == newItem
        }
    }
}
