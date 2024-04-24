package com.example.todo

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.Delegates.notNull

object SharedPrefs {

    private const val PREFS_NAME = "todo-prefs"
    private const val KEY_CHECKED = "checked"
    private const val KEY_UNCHECKED = "unchecked"

    private var sharedPrefs by notNull<SharedPreferences>()

    fun init(context: Context) {
        sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveCheckedColor(color: Int) {
        sharedPrefs.edit().putInt(KEY_CHECKED, color).apply()
    }

    fun saveUncheckedColor(color: Int) {
        sharedPrefs.edit().putInt(KEY_UNCHECKED, color).apply()
    }

    fun getColor(): Color {
        val checkedColor = sharedPrefs.getInt(KEY_CHECKED, R.color.black)
        val uncheckedColor = sharedPrefs.getInt(KEY_UNCHECKED, R.color.black)
        return Color(
            uncheckedColor = uncheckedColor,
            checkedColor = checkedColor
        )
    }
}

data class Color(
    val checkedColor: Int,
    val uncheckedColor: Int
)