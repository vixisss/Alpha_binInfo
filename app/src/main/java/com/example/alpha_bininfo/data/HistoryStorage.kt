package com.example.alpha_bininfo.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.core.content.edit

class HistoryStorage(context: Context) {
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("bin_history", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val historyKey = "history_list"

    fun addToHistory(bin: String, binInfo: BinInfo) {
        val history = getHistory().toMutableList()
        history.add(0, HistoryItem(bin, binInfo))
        saveHistory(history)
    }

    fun getHistory(): List<HistoryItem> {
        val json = sharedPref.getString(historyKey, null)
        return json?.let {
            gson.fromJson(it, object : TypeToken<List<HistoryItem>>() {}.type)
        } ?: emptyList()
    }

    private fun saveHistory(history: List<HistoryItem>) {
        sharedPref.edit { putString(historyKey, gson.toJson(history)) }
    }
}

data class HistoryItem(
    val bin: String?,
    val info: BinInfo?
)