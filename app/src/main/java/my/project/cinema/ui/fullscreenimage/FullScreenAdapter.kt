package my.project.cinema.ui.fullscreenimage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.project.cinema.R
import my.project.cinema.databinding.FullScreenItemLayoutBinding
import my.project.cinema.json.screens.ScreenImage

class FullScreenAdapter :
    PagingDataAdapter<ScreenImage, FullScreenAdapter.ScreenViewHolder>(DiffUtilCallBack()) {

    inner class ScreenViewHolder(val binding: FullScreenItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ScreenViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                Glide
                    .with(ivScreen.context)
                    .load(it.previewUrl)
                    .error(R.drawable.cover)
                    .into(ivScreen)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenViewHolder {
        return ScreenViewHolder(
            FullScreenItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

}

class DiffUtilCallBack : DiffUtil.ItemCallback<ScreenImage>() {
    override fun areItemsTheSame(oldItem: ScreenImage, newItem: ScreenImage): Boolean =
        oldItem.imageUrl == newItem.imageUrl

    override fun areContentsTheSame(oldItem: ScreenImage, newItem: ScreenImage): Boolean =
        oldItem == newItem
}