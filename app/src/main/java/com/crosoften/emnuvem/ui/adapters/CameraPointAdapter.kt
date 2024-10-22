package com.crosoften.emnuvem.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.crosoften.emnuvem.data.model.CameraPointModel
import com.crosoften.emnuvem.databinding.CameraPointItemBinding

class CameraPointAdapter : RecyclerView.Adapter<CameraPointAdapter.ViewHolder>() {

    private var stockList: AsyncListDiffer<CameraPointModel> =
        AsyncListDiffer(this, DiffCallBack)
//     private lateinit var listener: OnCameraClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = CameraPointItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return stockList.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stockList.currentList[position])
    }

    object DiffCallBack : DiffUtil.ItemCallback<CameraPointModel>() {
        override fun areItemsTheSame(
            oldItem: CameraPointModel,
            newItem: CameraPointModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CameraPointModel,
            newItem: CameraPointModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    fun updateList(list: List<CameraPointModel>) {
        stockList.submitList(list)
    }

//    fun setListener(listener: OnCameraClickListener) {
//        this.listener = listener
//    }

    inner class ViewHolder(private val binding: CameraPointItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CameraPointModel) {
            binding.time.text = item.time
            Glide.with(binding.root)
                .load(item.picture)
                .optionalCenterCrop()
                .into(binding.image)
//            if (::listener.isInitialized) {
//                binding.root.setOnClickListener {
//                    listener.onClick()
//                }
//            }
        }
    }
}