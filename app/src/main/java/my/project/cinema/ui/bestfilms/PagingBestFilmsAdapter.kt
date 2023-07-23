package my.project.cinema.ui.bestfilms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.project.cinema.databinding.BestFilmsItemLayoutBinding
import my.project.cinema.json.bestfilms.BestFilmsList

class PagingBestFilmsAdapter :
    PagingDataAdapter<BestFilmsList, PagingBestFilmsAdapter.BestFilmsViewHolder>(DiffUtilCallBack()) {

    var onClick: ((String) -> Unit)? = null

    inner class BestFilmsViewHolder(val binding: BestFilmsItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun onFilmClick(listener: (String) -> Unit) {
        onClick = listener
    }

    override fun onBindViewHolder(holder: BestFilmsViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            tvTitle.text = item?.nameRu ?: item?.nameEn ?: ""
            tvGenre.text = item?.genres?.joinToString(", ") { it.genre }
            tvRating.text = item?.rating.toString()
            tvYear.text = item?.year ?: ""
            item?.let {
                Glide
                    .with(ivPoster.context)
                    .load(it.posterUrlPreview)
                    .into(ivPoster)
            }
            cvTop.setOnClickListener {
                onClick?.let {
                    it(item?.filmId.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestFilmsViewHolder {
        return BestFilmsViewHolder(
            BestFilmsItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

}

class DiffUtilCallBack : DiffUtil.ItemCallback<BestFilmsList>() {
    override fun areItemsTheSame(oldItem: BestFilmsList, newItem: BestFilmsList): Boolean =
        oldItem.filmId == newItem.filmId

    override fun areContentsTheSame(oldItem: BestFilmsList, newItem: BestFilmsList): Boolean =
        oldItem == newItem
}