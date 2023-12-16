package com.akhundovmurad.tayqatechtask.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akhundovmurad.tayqatechtask.databinding.FilterItemBinding
import com.akhundovmurad.tayqatechtask.filter.FilterItem
import com.akhundovmurad.tayqatechtask.filter.FilterType

class FilterAdapter(private var items: List<FilterItem>) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {
    inner class FilterViewHolder(val binding: FilterItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FilterItemBinding.inflate(inflater, parent, false)
        return FilterViewHolder(binding)
    }
    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val item = items[position]
        val b = holder.binding


        b.text.text = item.name

        b.checkbox.setOnCheckedChangeListener { _, isChecked ->
            item.isChecked = isChecked
        }

        b.checkbox.isChecked = item.isChecked

    }

    override fun getItemCount(): Int = items.size

    fun getSelectedItems(filterType: FilterType): List<FilterItem> {


        return items.filter { it.type == filterType && it.isChecked }
    }

    fun updateData(newItems: List<FilterItem>) {
        items = newItems
        notifyDataSetChanged()
    }
    fun restoreState(selectedItems: List<FilterItem>) {
        items.forEach { item ->
            val selectedItem = selectedItems.find { it.name == item.name && it.type == item.type }
            selectedItem?.let {
                item.isChecked = it.isChecked
            }
        }
        notifyDataSetChanged()
    }
}
