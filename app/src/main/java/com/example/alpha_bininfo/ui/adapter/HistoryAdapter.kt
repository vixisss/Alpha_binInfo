package com.example.alpha_bininfo.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alpha_bininfo.R
import com.example.alpha_bininfo.data.HistoryItem

class HistoryAdapter(
    private var binInfo: List<HistoryItem> = emptyList()
) : RecyclerView.Adapter<HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int = binInfo.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = binInfo.getOrNull(position)
        item?.let { holder.bind(it) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<HistoryItem>) {
        binInfo = newList
        notifyDataSetChanged()
    }
}