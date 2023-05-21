package my.project.cinema.ui.allactor

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.project.cinema.databinding.AllActorItemLayoutBinding
import my.project.cinema.json.actors.EnumKey
import my.project.cinema.json.actors.StaffActorItem

class AllActorAdapter : RecyclerView.Adapter<AllActorAdapter.ActorViewHolder>() {

    var onClick: ((String) -> Unit)? = null

    inner class ActorViewHolder(val binding: AllActorItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun onFilmClick(listener: (String) -> Unit) {
        onClick = listener
    }

    private var actor: MutableList<StaffActorItem> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<StaffActorItem>): List<StaffActorItem> {
//        this.actor = data
        for (element in data) {
            if (element.professionKey == EnumKey.ACTOR)
                this.actor.add(element)
            Log.d("adapterActorLog", "$data")
            notifyDataSetChanged()
        }
        return this.actor
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            AllActorItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val item = actor.getOrNull(position)
        holder.binding.apply {
            if (item?.nameRu != null) {
                tvName.text = item.nameRu
            } else {
                tvName.text = item?.nameEn
            }
            tvProfession.text = item?.description ?: ""

            actor.let {
                Glide.with(ivActor.context)
                    .load(item?.posterUrl)
                    .into(ivActor)
            }


            cvAllActor.setOnClickListener {
                onClick?.let {
                    it(item?.staffId.toString())
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return actor.size
    }
}