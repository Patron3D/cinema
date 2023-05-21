package my.project.cinema.ui.random

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.project.cinema.databinding.RandomItemLayoutBinding
import my.project.cinema.json.random.RandomList

class RandomPagingAdapter :
    PagingDataAdapter<RandomList, RandomPagingAdapter.RandomViewHolder>(DiffUtilCallBack()) {

    var onClick: ((String) -> Unit)? = null

    inner class RandomViewHolder(val binding: RandomItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun onFilmClick(listener: (String) -> Unit) {
        onClick = listener
    }

    override fun onBindViewHolder(holder: RandomViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            tvTitle.text = item?.nameRu ?: item?.nameOriginal ?: ""
            tvGenre.text = item?.genres?.joinToString(", ") { it.genre }
            if (item?.ratingKinopoisk == null) {
                tvRating.visibility = View.INVISIBLE
                ivRating.visibility = View.INVISIBLE
            } else {
                tvRating.text = item.ratingKinopoisk.toString()
            }
            tvYear.text = item?.year.toString()
            item?.let {
                Glide
                    .with(ivPoster.context)
                    .load(it.posterUrlPreview)
                    .into(ivPoster)
            }
            cvRandom.setOnClickListener {
                onClick?.let {
                    it(item?.kinopoiskId.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomViewHolder {
        return RandomViewHolder(
            RandomItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

}

class DiffUtilCallBack : DiffUtil.ItemCallback<RandomList>() {
    override fun areItemsTheSame(oldItem: RandomList, newItem: RandomList): Boolean =
        oldItem.kinopoiskId == newItem.kinopoiskId

    override fun areContentsTheSame(oldItem: RandomList, newItem: RandomList): Boolean =
        oldItem == newItem
}