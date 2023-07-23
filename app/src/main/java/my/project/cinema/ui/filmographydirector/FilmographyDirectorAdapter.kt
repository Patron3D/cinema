package my.project.cinema.ui.filmographydirector

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.project.cinema.databinding.FilmographyDirectorItemLayoutBinding
import my.project.cinema.json.actor.Films

class FilmographyDirectorAdapter :
    RecyclerView.Adapter<FilmographyDirectorAdapter.DirectorViewHolder>() {

    var onClick: ((String) -> Unit)? = null

    inner class DirectorViewHolder(val binding: FilmographyDirectorItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun onFilmClick(listener: (String) -> Unit) {
        onClick = listener
    }

    private var filmList: MutableList<Films> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Films>): MutableList<Films> {
        filmList.clear()
        this.filmList = data.toMutableList()
        this.filmList.sortByDescending { it.rating }
        Log.d("adapterLog", "$data")
        notifyDataSetChanged()
        return this.filmList
    }

    override fun onBindViewHolder(holder: DirectorViewHolder, position: Int) {
        val item = filmList.getOrNull(position)
        with(holder.binding) {
            if (item?.nameRu != null) {
                tvTitle.text = item.nameRu
            } else {
                tvTitle.text = item?.nameEn
            }

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
            cvFilmographyDirector.setOnClickListener {
                onClick?.let {
                    it(item?.filmId.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectorViewHolder {
        return DirectorViewHolder(
            FilmographyDirectorItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    private companion object {
        const val URL_IMAGE = "https://kinopoiskapiunofficial.tech/images/posters/kp_small/"
        const val JPG = ".jpg"
    }

}