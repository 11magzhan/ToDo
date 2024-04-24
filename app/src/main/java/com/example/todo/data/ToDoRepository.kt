package com.example.todo.data

import android.content.Context

class ListItemRepository(context: Context) {
    private val listDatabaseHelper: DatabaseHelper = DatabaseHelper(context)

    fun insertListItem(listItem: ListItem) {
        listDatabaseHelper.insertListItem(listItem)
    }

    fun getAllListItems(): ArrayList<ListItem> {
        return listDatabaseHelper.getAllListItems()
    }

    fun deleteListItem(itemId: Long): Int {
        return listDatabaseHelper.deleteListItem(itemId)
    }

    fun editListItem(listItem: ListItem) {
        return listDatabaseHelper.editListItem(listItem)
    }
}

class ToDoItemRepository(context: Context) {
    private val toDoItemDatabaseHelper: ToDoItemDatabaseHelper = ToDoItemDatabaseHelper(context)

    fun insertToDoItem(toDoItem: ToDoItem) {
        toDoItemDatabaseHelper.insertToDoItem(toDoItem)
    }

    fun getAllToDoItems(listId: Long): ArrayList<ToDoItem> {
        return toDoItemDatabaseHelper.getAllToDoItems(listId)
    }

    fun deleteToDoItem(toDoItemId: Long): Int {
        return toDoItemDatabaseHelper.deleteToDoItem(toDoItemId)
    }

    fun editToDoItem(id: Long, text: String) {
        return toDoItemDatabaseHelper.editToDoItem(id, text)
    }

    fun deleteAllToDoItems() {
        toDoItemDatabaseHelper.deleteAllToDoItems()
    }
}
