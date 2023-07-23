package my.project.cinema.ui.alldirectorpage

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.project.cinema.databinding.AllDirectorItemLayoutBinding
import my.project.cinema.json.actors.EnumKey
import my.project.cinema.json.actors.StaffActorItem

class AllDirectorAdapter : RecyclerView.Adapter<AllDirectorAdapter.DirectorViewHolder>() {

    var onClick: ((String) -> Unit)? = null

    inner class DirectorViewHolder(val binding: AllDirectorItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun onFilmClick(listener: (String) -> Unit) {
        onClick = listener
    }

    private var director: MutableList<StaffActorItem> = mutableListOf()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<StaffActorItem>): List<StaffActorItem> {
        for (element in data) {
            if (element.professionKey != EnumKey.ACTOR)
                this.director.add(element)
            Log.d("adapterActorLog", "$data")
            notifyDataSetChanged()
        }
        return this.director
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectorViewHolder {
        return DirectorViewHolder(
            AllDirectorItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DirectorViewHolder, position: Int) {
        val item = director.getOrNull(position)
        holder.binding.apply {
            if (item?.professionKey != EnumKey.ACTOR) {
                if (item?.nameRu != null) {
                    tvName.text = item.nameRu
                } else {
                    tvName.text = item?.nameEn
                }
                tvProfession.text = item?.professionText?.substringBeforeLast('ы') ?: ""

                director.let {
                    Glide.with(ivDirector.context)
                        .load(item?.posterUrl)
                        .into(ivDirector)
                }

            }
            cvAllDirector.setOnClickListener {
                onClick?.let {
                    it(item?.staffId.toString())
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return director.size
    }
}