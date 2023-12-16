package com.akhundovmurad.tayqatechtask.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akhundovmurad.tayqatechtask.databinding.PersonItemBinding

class MainAdapter(private var itemList: List<String>) : RecyclerView.Adapter<MainAdapter.ItemDesignHolder>() {
    inner class ItemDesignHolder(val binding: PersonItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDesignHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PersonItemBinding.inflate(inflater, parent, false)
        return ItemDesignHolder(binding)
    }
    override fun getItemCount(): Int = itemList.size
    override fun onBindViewHolder(holder: ItemDesignHolder, position: Int) {
        val b = holder.binding
        val name = itemList[position]
        b.name.text = name
    }
    fun updateData(newItemList: List<String>) {
        itemList = newItemList
        notifyDataSetChanged()
    }
}
