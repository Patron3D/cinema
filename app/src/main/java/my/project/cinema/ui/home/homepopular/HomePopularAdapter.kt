package my.project.cinema.ui.home.homepopular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.project.cinema.databinding.HomePopularItemLayoutBinding
import my.project.cinema.json.bestfilms.BestFilmsList

class HomePopularAdapter :
    PagingDataAdapter<BestFilmsList, HomePopularAdapter.HomePopularViewHolder>(DiffUtilCallBack()) {

    var onClick: ((String) -> Unit)? = null

    inner class HomePopularViewHolder(val binding: HomePopularItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun onFilmClick(listener: (String) -> Unit) {
        onClick = listener
    }

    override fun onBindViewHolder(holder: HomePopularViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            tvTitle.text = item?.nameRu ?: ""
            tvGenre.text = item?.genres?.joinToString(", ") { it.genre }
            if (item?.rating == null) {
                tvRating.visibility = View.INVISIBLE
                ivRating.visibility = View.INVISIBLE
            } else {
                tvRating.text = item.rating.toString()
            }
            item?.let {
                Glide
                    .with(ivPoster.context)
                    .load(it.posterUrlPreview)
                    .into(ivPoster)
            }
            cvHomePopular.setOnClickListener {
                onClick?.let {
                    it(item?.filmId.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePopularViewHolder {
        return HomePopularViewHolder(
            HomePopularItemLayoutBinding.inflate(
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