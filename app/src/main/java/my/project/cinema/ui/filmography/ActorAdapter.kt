package my.project.cinema.ui.filmography

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.project.cinema.databinding.FilmsItemLayoutBinding
import my.project.cinema.json.actor.Films

class ActorAdapter : RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    var onClick: ((String) -> Unit)? = null

    inner class ActorViewHolder(val binding: FilmsItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun onFilmClick(listener: (String) -> Unit) {
        onClick = listener
    }

    private var actorList: MutableList<Films> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Films>): MutableList<Films> {
        actorList.clear()
        data.onEach {
            if (it.professionKey == ACTOR) {
                this.actorList.add(it)
            }
        }
        this.actorList.sortByDescending { it.rating }
        Log.d("adapterLog", "$data")
        notifyDataSetChanged()
        return this.actorList
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val item = actorList.getOrNull(position)
        with(holder.binding) {
            if (item?.nameRu != null) {
                tvTitle.text = item.nameRu
            } else {
                tvTitle.text = item?.nameEn
            }

            tvRole.text = item?.description

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
            cvFilms.setOnClickListener {
                onClick?.let {
                    it(item?.filmId.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            FilmsItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return actorList.size
    }

    private companion object {
        const val URL_IMAGE = "https://kinopoiskapiunofficial.tech/images/posters/kp_small/"
        const val ACTOR = "ACTOR"
        const val JPG = ".jpg"
    }

}