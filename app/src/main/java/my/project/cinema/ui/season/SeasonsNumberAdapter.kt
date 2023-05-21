package my.project.cinema.ui.season

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import my.project.cinema.R
import my.project.cinema.databinding.SeasonItemLayoutBinding
import my.project.cinema.json.seasons.Item

class SeasonsNumberAdapter : RecyclerView.Adapter<SeasonsNumberAdapter.SeasonViewHolder>() {

    var onClick: ((String) -> Unit)? = null

    inner class SeasonViewHolder(val binding: SeasonItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun onFilmClick(listener: (String) -> Unit) {
        onClick = listener
    }

    private var seasonNumber: List<Item> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Item>) {
        this.seasonNumber = data
        Log.d("adapterLog", "$data")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        return SeasonViewHolder(
            SeasonItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        val item = seasonNumber.getOrNull(position)
        val number = seasonNumber[0].number
        holder.binding.apply {
            if (number == 1) {
                btnSeason.text = item?.number.toString()
            } else {
                btnSeason.text = item?.number!!.plus(1).toString()
            }

            btnSeason.setOnClickListener {
                onClick?.let {
                    if (number == 0) {
                        it(
                            item?.number!!.plus(1)
                                .toString() + " сезон,  " + holder.binding.root.resources.getQuantityString(
                                R.plurals.count_series, item.episodes.size, item.episodes.size
                            )
                        )
                    } else {
                        it(
                            item?.number.toString() + " сезон,  " + holder.binding.root.resources.getQuantityString(
                                R.plurals.count_series, item?.episodes!!.size, item.episodes.size
                            )
                        )
                    }
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return seasonNumber.size
    }
}