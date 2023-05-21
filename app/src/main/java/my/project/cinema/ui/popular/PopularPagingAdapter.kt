package my.project.cinema.ui.popular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.project.cinema.databinding.PopularItemLayoutBinding
import my.project.cinema.json.bestfilms.BestFilmsList

class PopularPagingAdapter :
    PagingDataAdapter<BestFilmsList, PopularPagingAdapter.PopularViewHolder>(DiffUtilCallBack()) {

    var onClick: ((String) -> Unit)? = null

    inner class PopularViewHolder(val binding: PopularItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun onFilmClick(listener: (String) -> Unit) {
        onClick = listener
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            tvTitle.text = item?.nameRu ?: item?.nameEn ?: ""
            tvGenre.text = item?.genres?.joinToString(", ") { it.genre }
            if (item?.rating == null) {
                tvRating.visibility = View.INVISIBLE
                ivRating.visibility = View.INVISIBLE
            } else {
                tvRating.text = item.rating.toString()
            }
            tvYear.text = item?.year ?: ""
            item?.let {
                Glide
                    .with(ivPoster.context)
                    .load(it.posterUrlPreview)
                    .into(ivPoster)
            }
            cvPopular.setOnClickListener {
                onClick?.let {
                    it(item?.filmId.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(
            PopularItemLayoutBinding.inflate(
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