package my.project.cinema.ui.similarfilms

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.project.cinema.databinding.SimilarFilmsItemLayoutBinding
import my.project.cinema.json.similar.SimilarFilm

class SimilarFilmsAdapter : RecyclerView.Adapter<SimilarFilmsAdapter.SimilarViewHolder>() {

    var onClick: ((String) -> Unit)? = null

    inner class SimilarViewHolder(val binding: SimilarFilmsItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun onFilmClick(listener: (String) -> Unit) {
        onClick = listener
    }

    private var similar: List<SimilarFilm> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<SimilarFilm>) {
        this.similar = data
        Log.d("similarAdapterLog", "$data")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        return SimilarViewHolder(
            SimilarFilmsItemLayoutBinding.inflate(
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
            cvSimilarFilms.setOnClickListener {
                onClick?.let {
                    it(item?.filmId.toString())
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return this.similar.size
    }
}