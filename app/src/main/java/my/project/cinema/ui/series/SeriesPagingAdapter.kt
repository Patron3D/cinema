package my.project.cinema.ui.series

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.project.cinema.databinding.SeriesItemLayoutBinding
import my.project.cinema.json.seriesall.SeriesList

class SeriesPagingAdapter :
    PagingDataAdapter<SeriesList, SeriesPagingAdapter.SeriesViewHolder>(DiffUtilCallBack()) {

    var onClick: ((String) -> Unit)? = null

    inner class SeriesViewHolder(val binding: SeriesItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun onFilmClick(listener: (String) -> Unit) {
        onClick = listener
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            tvTitle.text = item?.nameRu ?: item?.nameOriginal
            tvGenre.text = item?.genres?.joinToString(", ") { it.genre }
            if (item?.ratingKinopoisk == null) {
                tvRating.visibility = View.INVISIBLE
                ivRating.visibility = View.INVISIBLE
            } else {
                tvRating.text = item.ratingKinopoisk.toString()
            }
            tvYear.text = item?.year?.toString()
            item?.let {
                Glide
                    .with(ivPoster.context)
                    .load(it.posterUrlPreview)
                    .into(ivPoster)

            }
            cvSeries.setOnClickListener {
                onClick?.let {
                    it(item?.kinopoiskId.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        return SeriesViewHolder(
            SeriesItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

}

class DiffUtilCallBack : DiffUtil.ItemCallback<SeriesList>() {
    override fun areItemsTheSame(oldItem: SeriesList, newItem: SeriesList): Boolean =
        oldItem.kinopoiskId == newItem.kinopoiskId

    override fun areContentsTheSame(oldItem: SeriesList, newItem: SeriesList): Boolean =
        oldItem == newItem
}