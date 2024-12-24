package com.emnuvem.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emnuvem.data.model.response.faqs.Faq
import com.emnuvem.databinding.FaqItemBinding

class FaqAdapter(private val list: List<Faq>) : RecyclerView.Adapter<FaqAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: FaqItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Faq){
            binding.question.text = item.question
            binding.answer.text = item.answer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FaqItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}