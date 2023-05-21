package my.project.cinema.ui.topten

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.project.cinema.databinding.TopTenItemLayoutBinding
import my.project.cinema.json.actor.Films

class TopTenAdapter : RecyclerView.Adapter<TopTenAdapter.TopViewHolder>() {

    var onClick: ((String) -> Unit)? = null

    inner class TopViewHolder(val binding: TopTenItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun onFilmClick(listener: (String) -> Unit) {
        onClick = listener
    }

    private var topList: MutableList<Films> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Films>): MutableList<Films> {
        this.topList = data.toMutableList()
        this.topList.sortByDescending { it.rating }
        Log.d("adapterLog", "$data")
        notifyDataSetChanged()
        return this.topList
    }

    override fun onBindViewHolder(holder: TopViewHolder, position: Int) {
        val item = topList.getOrNull(position)
        with(holder.binding) {
            if (item?.nameRu != null) {
                tvTitle.text = item.nameRu
            } else {
                tvTitle.text = item?.nameEn
            }

            if (item?.rating == null) {
                tvRating.visibility = View.INVISIBLE
                ivRating.visibility = View.INVISIBLE
            } else {
                tvRating.text = item.rating.toString()
            }
            item?.let {
                Glide
                    .with(ivPoster.context)
                    .load(URL_IMAGE + "${item.filmId}" + JPG)
                    .into(ivPoster)
            }
            cvTopTen.setOnClickListener {
                onClick?.let {
                    it(item?.filmId.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopViewHolder {
        return TopViewHolder(
            TopTenItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return if (topList.size < MAX) {
            topList.size
        } else {
            10
        }
    }

    private companion object {
        const val MAX = 11
        const val URL_IMAGE = "https://kinopoiskapiunofficial.tech/images/posters/kp_small/"
        const val JPG = ".jpg"
    }

}