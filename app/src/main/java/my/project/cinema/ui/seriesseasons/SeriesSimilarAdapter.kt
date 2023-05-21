package my.project.cinema.ui.seriesseasons

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.project.cinema.databinding.FilmSimilarItemLayoutBinding
import my.project.cinema.json.similar.SimilarFilm

class SeriesSimilarAdapter : RecyclerView.Adapter<SeriesSimilarAdapter.SimilarViewHolder>() {

    inner class SimilarViewHolder(val binding: FilmSimilarItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var similar: List<SimilarFilm> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<SimilarFilm>) {
        this.similar = data
        Log.d("similarAdapterLog", "$data")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        return SimilarViewHolder(
            FilmSimilarItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
        val item = similar.getOrNull(position)
        holder.binding.apply {
            tvTitle.text = item?.nameRu ?: item?.nameOriginal ?: ""
            similar.let {
                Glide.with(ivPoster.context)
                    .load(item?.posterUrlPreview)
                    .into(ivPoster)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (similar.size < MAX) {
            this.similar.size
        } else {
            20
        }
    }

    private companion object {
        const val MAX = 21
    }

}