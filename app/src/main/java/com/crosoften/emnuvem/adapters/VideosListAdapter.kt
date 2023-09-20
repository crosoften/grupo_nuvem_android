package com.crosoften.emnuvem.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.crosoften.emnuvem.databinding.VideosListItemBinding
import com.crosoften.emnuvem.model.VideoModel

class VideosListAdapter : RecyclerView.Adapter<VideosListAdapter.ViewHolder>() {

    private var stockList: AsyncListDiffer<VideoModel> =
        AsyncListDiffer(this, DiffCallBack)
//     private lateinit var listener: OnCameraClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = VideosListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return stockList.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stockList.currentList[position])
    }

    object DiffCallBack : DiffUtil.ItemCallback<VideoModel>() {
        override fun areItemsTheSame(
            oldItem: VideoModel,
            newItem: VideoModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: VideoModel,
            newItem: VideoModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    fun updateList(list: List<VideoModel>) {
        stockList.submitList(list)
    }

//    fun setListener(listener: OnCameraClickListener) {
//        this.listener = listener
//    }

    inner class ViewHolder(private val binding: VideosListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoModel) {
            binding.title.text = item.title
            binding.camera.text = item.camera
            binding.address.text = item.address
            binding.date.text = item.date
            Glide.with(binding.root)
                .load(item.picture)
                .optionalCenterCrop()
                .into(binding.img)
//            if (::listener.isInitialized) {
//                binding.root.setOnClickListener {
//                    listener.onClick()
//                }
//            }
        }
    }
}