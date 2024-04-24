package com.example.todo.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class ToDoItemDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "todo_database.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "todo_items"
        const val COLUMN_ID = "id"
        const val COLUMN_LIST_ID = "list_id"
        const val COLUMN_TEXT = "text"
    }

    override fun onCreate(p0: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_LIST_ID INTEGER, $COLUMN_TEXT TEXT)"
        p0.execSQL(createTableQuery)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        // no-op
    }

    fun insertToDoItem(toDoItem: ToDoItem) {
        val values = ContentValues().apply {
            put(COLUMN_LIST_ID, toDoItem.listId)
            put(COLUMN_TEXT, toDoItem.text)

        }
        writableDatabase.insert(TABLE_NAME, null, values)
       // writableDatabase.close()
    }

    fun getAllToDoItems(listId: Long): ArrayList<ToDoItem> {
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_LIST_ID = ?"
        val db = readableDatabase
        val cursor = db.rawQuery(query, arrayOf(listId.toString()))
        val toDoItems = ArrayList<ToDoItem>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID).coerceAtLeast(0))
                val text = cursor.getString(cursor.getColumnIndex(COLUMN_TEXT).coerceAtLeast(0))
                toDoItems.add(ToDoItem(id, listId, text))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return toDoItems
    }

    fun editToDoItem(id: Long, text: String) {
        val values = ContentValues().apply {
            put(COLUMN_ID, id)
            put(COLUMN_TEXT, text)
        }
        writableDatabase.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun deleteToDoItem(id: Long): Int {
        return writableDatabase.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun deleteAllToDoItems() {
        writableDatabase.delete(TABLE_NAME, null, null)
        writableDatabase.close()
    }
}