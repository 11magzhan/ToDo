package com.example.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.data.ToDoItem
import com.example.todo.databinding.ItemTodoBinding

class ToDoItemAdapter(
    private val values: MutableList<ToDoItem> = mutableListOf(),
    val onEditListener: (Long) -> Unit,
    val onDeleteListener: (Long) -> Unit
): RecyclerView.Adapter<ToDoItemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(
            item = item,
            onEditListener = onEditListener,
            onDeleteListener = onDeleteListener
        )
    }

    fun updateValues(newValues: List<ToDoItem>) {
        val calculateDiff = DiffUtil.calculateDiff(ToDoItemCallback(values, newValues))
        values.clear()
        values.addAll(newValues)
        calculateDiff.dispatchUpdatesTo(this)
    }


    inner class ViewHolder(private val binding: ItemTodoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ToDoItem,
            onEditListener: (Long) -> Unit,
            onDeleteListener: (Long) -> Unit
        ) = with(binding) {
            textItem.text = item.text
            deleteBtn.setOnClickListener{
                onDeleteListener(item.id)
            }
            editBtn.setOnClickListener{
                onEditListener(item.id)
            }
            checkbox.setOnCheckedChangeListener{ buttonView, isChecked ->
                if (checkbox.isChecked) {
                    // todo
                }
            }
        }

        init {
            with(binding) {
                if (checkbox.isChecked) {
                    // todo
                }
            }
        }
    }
}