package com.crosoften.emnuvem.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.crosoften.emnuvem.R
import com.crosoften.emnuvem.data.model.CameraModel
import com.crosoften.emnuvem.databinding.CameraMosaicItemBinding
import com.crosoften.emnuvem.ui.listeners.OnCameraClickListener
import com.crosoften.emnuvem.ultils.calculateScreenWidth

class CameraMosaicAdapter(
    val context: Context
) : RecyclerView.Adapter<CameraMosaicAdapter.ViewHolder>() {

    private var stockList: AsyncListDiffer<CameraModel> =
        AsyncListDiffer(this, DiffCallBack)
     private lateinit var listener: OnCameraClickListener
    private val screenWidth = calculateScreenWidth(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = CameraMosaicItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return stockList.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stockList.currentList[position])
    }

    object DiffCallBack : DiffUtil.ItemCallback<CameraModel>() {
        override fun areItemsTheSame(
            oldItem: CameraModel,
            newItem: CameraModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CameraModel,
            newItem: CameraModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    fun updateList(list: List<CameraModel>) {
        stockList.submitList(list)
    }

    fun setListener(listener: OnCameraClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(private val binding: CameraMosaicItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CameraModel) {
            binding.name.text = item.name
//            Glide.with(binding.root)
//                .load(item.picture)
//                .placeholder(R.drawable.cameras_icon)
////                .optionalCenterCrop()
//                .into(binding.img)
            if (::listener.isInitialized) {
                binding.root.setOnClickListener {
                    listener.onClick(item)
                }
            }
        }
    }
}