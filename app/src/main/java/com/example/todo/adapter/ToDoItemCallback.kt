package com.example.todo.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.todo.data.ToDoItem

class ToDoItemCallback(
    private val oldList: List<ToDoItem>,
    private val newList: List<ToDoItem>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}