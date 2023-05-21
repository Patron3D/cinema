package my.project.cinema.ui.filmography

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.project.cinema.databinding.FilmsItemLayoutBinding
import my.project.cinema.json.actor.Films

class VoiceAdapter : RecyclerView.Adapter<VoiceAdapter.ActorViewHolder>() {

    var onClick: ((String) -> Unit)? = null

    inner class ActorViewHolder(val binding: FilmsItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun onFilmClick(listener: (String) -> Unit) {
        onClick = listener
    }

    private var voiceList: MutableList<Films> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Films>): MutableList<Films> {
        voiceList.clear()
        data.onEach {
            if (it.professionKey == VOICE_MALE || it.professionKey == VOICE_FEMALE) {
                this.voiceList.add(it)
            }
        }
        this.voiceList.sortByDescending { it.rating }
        notifyDataSetChanged()
        return this.voiceList
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val item = voiceList.getOrNull(position)
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
        return voiceList.size
    }

    private companion object {
        const val URL_IMAGE = "https://kinopoiskapiunofficial.tech/images/posters/kp_small/"
        const val VOICE_MALE = "VOICE_MALE"
        const val VOICE_FEMALE = "VOICE_FEMALE"
        const val JPG = ".jpg"
    }
}