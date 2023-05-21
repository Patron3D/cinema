package my.project.cinema.ui.filmpage

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.project.cinema.databinding.DirectorItemLayoutBinding
import my.project.cinema.json.actors.EnumKey
import my.project.cinema.json.actors.StaffActorItem

class FilmDirectorAdapter : RecyclerView.Adapter<FilmDirectorAdapter.DirectorViewHolder>() {

    var onClick: ((String) -> Unit)? = null

    inner class DirectorViewHolder(val binding: DirectorItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun onFilmClick(listener: (String) -> Unit) {
        onClick = listener
    }

    private var profession: MutableList<StaffActorItem> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<StaffActorItem>): List<StaffActorItem> {
        for (element in data) {
            if (element.professionKey != EnumKey.ACTOR)
                this.profession.add(element)
            Log.d("adapterActorLog", "$data")
            notifyDataSetChanged()
        }
        return this.profession
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectorViewHolder {
        return DirectorViewHolder(
            DirectorItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DirectorViewHolder, position: Int) {
        val item = profession.getOrNull(position)
        holder.binding.apply {
            if (item?.professionKey != EnumKey.ACTOR && item?.nameRu != null) {
                tvProfession.text = item.professionText?.substringBeforeLast('ы') ?: ""
                tvName.text = item.nameRu

                profession.let {
                    Glide.with(ivPoster.context)
                        .load(item.posterUrl)
                        .into(ivPoster)
                }

            }
            cvDirector.setOnClickListener {
                onClick?.let {
                    it(item?.staffId.toString())
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (profession.size < MAX) {
            profession.size
        } else {
            6
        }
    }

    private companion object {
        const val MAX = 7
    }

}