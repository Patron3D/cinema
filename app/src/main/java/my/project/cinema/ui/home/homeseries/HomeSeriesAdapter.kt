package my.project.cinema.ui.home.homeseries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.project.cinema.databinding.HomeSeriesItemLayoutBinding
import my.project.cinema.json.seriesall.SeriesList

class HomeSeriesAdapter :
    PagingDataAdapter<SeriesList, HomeSeriesAdapter.HomeSeriesViewHolder>(DiffUtilCallBack()) {

    var onClick: ((String) -> Unit)? = null

    inner class HomeSeriesViewHolder(val binding: HomeSeriesItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun onFilmClick(listener: (String) -> Unit) {
        onClick = listener
    }

    override fun onBindViewHolder(holder: HomeSeriesViewHolder, position: Int) {
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
            item?.let {
                Glide
                    .with(ivPoster.context)
                    .load(it.posterUrlPreview)
                    .into(ivPoster)
            }
            cvHomeSeries.setOnClickListener {
                onClick?.let {
                    it(item?.kinopoiskId.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSeriesViewHolder {
        return HomeSeriesViewHolder(
            HomeSeriesItemLayoutBinding.inflate(
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