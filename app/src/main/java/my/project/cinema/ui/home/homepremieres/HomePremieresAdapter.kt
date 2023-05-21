package my.project.cinema.ui.home.homepremieres

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.project.cinema.databinding.HomePremieresItemLayoutBinding
import my.project.cinema.json.premieres.ListItems

class HomePremieresAdapter : RecyclerView.Adapter<HomePremieresAdapter.HomePremieresViewHolder>() {

    var onClick: ((String) -> Unit)? = null

    inner class HomePremieresViewHolder(val binding: HomePremieresItemLayoutBinding) :
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePremieresViewHolder {
        return HomePremieresViewHolder(
            HomePremieresItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomePremieresViewHolder, position: Int) {
        val item = data.getOrNull(position)
        holder.binding.apply {
            tvTitle.text = item?.nameRu ?: item?.nameEn ?: ""
            tvGenre.text = item?.genres?.joinToString(", ") { it.genre }
            data.let {
                Glide.with(ivPoster.context)
                    .load(item?.posterUrlPreview)
                    .into(ivPoster)
            }
            cvHomePremieres.setOnClickListener {
                onClick?.let {
                    it(item?.kinopoiskId.toString())
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return 20
    }

}