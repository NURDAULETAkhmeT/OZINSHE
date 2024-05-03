import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ozinshe.Data.Seasons.Season
import com.example.ozinshe.Data.Seasons.SeasonsList
import com.example.ozinshe.R
import com.example.ozinshe.databinding.DizaineSeriesSezonBinding

class SezonAdapter(val mItemClickListener: ItemClickListener) : RecyclerView.Adapter<SezonAdapter.ViewHolder>() {
    private var listAdapter = mutableListOf<SeasonsList>()
    private var selectedItemPosition = 0

    interface ItemClickListener {
        fun onItemClick(id: List<Season>?)
    }

    fun submitList(productList: SeasonsList) {
        listAdapter.clear()
        listAdapter.addAll(listOf(productList))
        notifyDataSetChanged()
    }

    inner class ViewHolder(internal val binding: DizaineSeriesSezonBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceType")
        fun bindItem(itemMovieMain: SeasonsList, position: Int) {
            val numbersList = (1..itemMovieMain.seasonNumber).toList()
            binding.text.text = "${numbersList.joinToString(", ")} ${binding.root.context.getString(R.string.series_sezon)}"



            // Обновление внешнего вида в зависимости от состояния выбора
            if (selectedItemPosition == position) {
                binding.text.setTextColor(Color.parseColor("#F9FAFB"))
                val backgroundColor = ContextCompat.getDrawable(context, R.drawable.shape_series_sezon)
                binding.fon.background = backgroundColor

            } else {
                binding.text.setTextColor(Color.parseColor("#374151"))
                val backgroundColor = ContextCompat.getDrawable(context, R.drawable.shape_series_sezon_false)
                binding.fon.background = backgroundColor
            }
        }

        init {
            itemView.setOnClickListener {
                // Сохранение выбранной позиции и обновление внешнего вида
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    selectedItemPosition = position
                    notifyDataSetChanged()
                    listAdapter?.get(position)?.seasons?.let { it -> mItemClickListener.onItemClick(it) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DizaineSeriesSezonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, parent.context)
    }

    override fun getItemCount(): Int {
        return listAdapter.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(listAdapter[position], position)
    }
    fun setSelectedItem(position: Int) {
        selectedItemPosition = position
        notifyDataSetChanged()
        listAdapter.getOrNull(position)?.seasons?.let { mItemClickListener.onItemClick(it) }
    }
}
