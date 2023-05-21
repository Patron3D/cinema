package my.project.cinema.ui.premieres

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.project.cinema.databinding.PremiersItemLayoutBinding
import my.project.cinema.json.premieres.ListItems

class PremiersAdapter : RecyclerView.Adapter<PremiersAdapter.PremiersViewHolder>() {

    var onClick: ((String) -> Unit)? = null

    inner class PremiersViewHolder(val binding: PremiersItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var data: List<ListItems> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<ListItems>) {
        this.data = data
        Log.d("adapterLog", "$data")
        notifyDataSetChanged()
    }

    fun onFilmClick(listener: (String) -> Unit) {
        onClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PremiersViewHolder {
        return PremiersViewHolder(
            PremiersItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PremiersViewHolder, position: Int) {
        val item = data.getOrNull(position)
        holder.binding.apply {
            tvTitle.text = item?.nameRu ?: item?.nameEn ?: ""
            tvGenre.text = item?.genres?.joinToString(", ") { it.genre }

            tvYear.text = item?.year.toString()

            data.let {
                Glide.with(ivPoster.context)
                    .load(item?.posterUrlPreview)
                    .into(ivPoster)
            }
            cvPremieres.setOnClickListener {
                onClick?.let {
                    it(item?.kinopoiskId.toString())
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}