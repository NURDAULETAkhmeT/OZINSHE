import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ozinshe.Data.Janr.janrItem
import com.example.ozinshe.databinding.DizaineHomeJanrBinding

class HomeJanr(val mItemClickListenerJanr : ItemClickListenerJanr): ListAdapter<janrItem, HomeJanr.ViewHolderJanr>(YourDiffCallback()) {

    interface ItemClickListenerJanr {
        fun onItemClickJanr(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderJanr {
        val binding = DizaineHomeJanrBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderJanr(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderJanr, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolderJanr(private val binding: DizaineHomeJanrBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(itemMovieMain: janrItem) {
            binding.homeJanrText.text = itemMovieMain.name
            Glide.with(itemView.context).load(itemMovieMain.link).into(binding.homeJanrImage)

            itemView.setOnClickListener {
                mItemClickListenerJanr.onItemClickJanr(itemMovieMain.id)
            }
        }
    }

    private class YourDiffCallback : DiffUtil.ItemCallback<janrItem>() {
        override fun areItemsTheSame(oldItem: janrItem, newItem: janrItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: janrItem, newItem: janrItem): Boolean {
            return oldItem == newItem
        }
    }
}
