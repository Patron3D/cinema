package my.project.cinema.ui.season

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import my.project.cinema.databinding.SeasonListItemLayoutBinding
import my.project.cinema.json.seasons.Episode

class SeasonInfoAdapter : RecyclerView.Adapter<SeasonInfoAdapter.SeasonViewHolder>() {

    var onClick: ((String) -> Unit)? = null

    inner class SeasonViewHolder(val binding: SeasonListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun onFilmClick(listener: (String) -> Unit) {
        onClick = listener
    }

    private var seasonList: List<Episode> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Episode>) {
        this.seasonList = data
        Log.d("adapterLog", "$data")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        return SeasonViewHolder(
            SeasonListItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        val item = seasonList.getOrNull(position)
        val number = seasonList[0].episodeNumber
        val name = item?.nameRu ?: item?.nameEn
        holder.binding.apply {
            if (number == 1) {
                tvName.text = "${item?.episodeNumber} серия. - " + ("$name")
            } else {
                tvName.text = "${item?.episodeNumber?.plus(1)} серия. - " + ("$name")
            }
            tvDate.text = "дата релиза: " + "${item?.releaseDate}"

        }
    }

    override fun getItemCount(): Int {
        return seasonList.size
    }
}