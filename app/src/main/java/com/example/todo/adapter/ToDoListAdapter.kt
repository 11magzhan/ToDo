package com.example.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.data.ListItem
import com.example.todo.databinding.ItemListBinding

class ToDoListAdapter(
    private val values: MutableList<ListItem> = mutableListOf(),
    val onEditListener: (Long) -> Unit,
    val onDeleteListener: (Long) -> Unit,
    val onItemClick: (Long, String) -> Unit
): RecyclerView.Adapter<ToDoListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListAdapter.ViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoListAdapter.ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(
            item = item,
            onEditListener = onEditListener,
            onDeleteListener = onDeleteListener,
            onItemClick = onItemClick
        )

    }

    override fun getItemCount(): Int = values.size

    fun updateValues(newValues: List<ListItem>) {
        val calculateDiff = DiffUtil.calculateDiff(ToDoListCallback(values, newValues))
        values.clear()
        values.addAll(newValues)
        calculateDiff.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ListItem,
            onEditListener: (Long) -> Unit,
            onDeleteListener: (Long) -> Unit,
            onItemClick: (Long, String) -> Unit
        ) = with(binding) {
            textItem.text = item.name
            deleteBtn.setOnClickListener {
                onDeleteListener(item.id)
            }
            editBtn.setOnClickListener {
                onEditListener(item.id)
            }
            textItem.setOnClickListener {
                onItemClick(item.id, item.name)
            }
        }
    }
}