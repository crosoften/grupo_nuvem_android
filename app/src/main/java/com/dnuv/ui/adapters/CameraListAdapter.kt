package com.dnuv.ui.adapters

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnuv.R
import com.dnuv.data.model.CameraModel
import com.dnuv.databinding.CameraListItemBinding
import com.dnuv.ui.listeners.OnCameraClickListener

class CameraListAdapter : RecyclerView.Adapter<CameraListAdapter.ViewHolder>() {

    private var stockList: AsyncListDiffer<CameraModel> =
        AsyncListDiffer(this, DiffCallBack)

    private var onItemClickListener: ((CameraModel, Int) -> Unit)? = null
    private var onEditClickListener: ((CameraModel, Int) -> Unit)? = null
    private var onDeleteClickListener: ((CameraModel, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = CameraListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int = stockList.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stockList.currentList[position])
    }

    object DiffCallBack : DiffUtil.ItemCallback<CameraModel>() {
        override fun areItemsTheSame(oldItem: CameraModel, newItem: CameraModel) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CameraModel, newItem: CameraModel) = oldItem == newItem
    }

    fun updateList(list: List<CameraModel>) {
        stockList.submitList(list)
    }

    fun getCurrentList(): List<CameraModel> = stockList.currentList.toList()

    fun setOnItemClickListener(listener: (CameraModel, Int) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnEditClickListener(listener: (CameraModel, Int) -> Unit) {
        onEditClickListener = listener
    }

    fun setOnDeleteClickListener(listener: (CameraModel, Int) -> Unit) {
        onDeleteClickListener = listener
    }

    inner class ViewHolder(private val binding: CameraListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CameraModel) {
            binding.name.text = item.name
            binding.address.text = item.address

            Glide.with(binding.root)
                .load(item.picture)
                .placeholder(R.color.gray)
                .optionalCenterCrop()
                .into(binding.img)

            binding.root.setOnClickListener {
                onItemClickListener?.invoke(item, absoluteAdapterPosition)
            }
            binding.editButton.setOnClickListener {
                onEditClickListener?.invoke(item, absoluteAdapterPosition)
            }
            binding.deleteButton.setOnClickListener {
                onDeleteClickListener?.invoke(item, absoluteAdapterPosition)
            }
        }
    }
}


