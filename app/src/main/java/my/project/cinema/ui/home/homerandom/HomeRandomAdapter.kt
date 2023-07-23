package my.project.cinema.ui.home.homerandom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.project.cinema.databinding.HomeRandomItemLayoutBinding
import my.project.cinema.json.random.RandomList

class HomeRandomAdapter :
    PagingDataAdapter<RandomList, HomeRandomAdapter.HomeRandomViewHolder>(DiffUtilCallBack()) {

    var onClick: ((String) -> Unit)? = null

    inner class HomeRandomViewHolder(val binding: HomeRandomItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun onFilmClick(listener: (String) -> Unit) {
        onClick = listener
    }

    override fun onBindViewHolder(holder: HomeRandomViewHolder, position: Int) {
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
            item?.let {
                Glide
                    .with(ivPoster.context)
                    .load(it.posterUrlPreview)
                    .into(ivPoster)
            }
            cvHomeRandom.setOnClickListener {
                onClick?.let {
                    it(item?.kinopoiskId.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRandomViewHolder {
        return HomeRandomViewHolder(
            HomeRandomItemLayoutBinding.inflate(
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