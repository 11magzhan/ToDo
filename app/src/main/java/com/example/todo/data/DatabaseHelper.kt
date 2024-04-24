package com.example.todo.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "list_database.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "list"
        const val COLUMN_NAME = "name"
        const val COLUMN_ID = "id"
    }

    override fun onCreate(p0: SQLiteDatabase) {
        val query = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT)"
        p0.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        // no-op
    }

    fun insertListItem(listItem: ListItem) {
        val values = ContentValues().apply {
            put(COLUMN_NAME, listItem.name)
        }
        writableDatabase.insert(TABLE_NAME, null, values)
    }

    fun getAllListItems(): ArrayList<ListItem> {
        val query = "SELECT * FROM $TABLE_NAME"
        val db = readableDatabase
        val cursor = db.rawQuery(query, null)
        val listItems = ArrayList<ListItem>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID).coerceAtLeast(0))
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME).coerceAtLeast(0))
                listItems.add(ListItem(id, name))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listItems
    }

    fun editListItem(listItem: ListItem) {
        val values = ContentValues().apply {
            put(COLUMN_NAME, listItem.name)
        }
        writableDatabase.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(listItem.id.toString()))
    }

    fun deleteListItem(itemId: Long): Int {
        return writableDatabase.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(itemId.toString()))
    }
}