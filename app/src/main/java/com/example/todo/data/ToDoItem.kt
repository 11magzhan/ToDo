package com.example.todo.data

data class ToDoItem(
    val id: Long = 0,
    val listId: Long,
    val text: String = ""
)
