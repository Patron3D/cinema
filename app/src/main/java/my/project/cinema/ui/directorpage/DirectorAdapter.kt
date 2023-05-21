package my.project.cinema.ui.directorpage

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.project.cinema.databinding.BestListItemLayoutBinding
import my.project.cinema.json.actor.Films

class DirectorAdapter : RecyclerView.Adapter<DirectorAdapter.DirectorViewHolder>() {

    var onClick: ((String) -> Unit)? = null

    inner class DirectorViewHolder(val binding: BestListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun onFilmClick(listener: (String) -> Unit) {
        onClick = listener
    }

    private var bestList: MutableList<Films> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Films>): MutableList<Films> {
        this.bestList = data.toMutableList()
        this.bestList.sortByDescending { it.rating }
        Log.d("adapterLog", "$data")
        notifyDataSetChanged()
        return this.bestList
    }

    override fun onBindViewHolder(holder: DirectorViewHolder, position: Int) {
        val item = bestList.getOrNull(position)
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectorViewHolder {
        return DirectorViewHolder(
            BestListItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return if (bestList.size < MAX) {
            bestList.size
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