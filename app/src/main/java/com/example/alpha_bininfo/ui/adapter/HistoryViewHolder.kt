package com.example.alpha_bininfo.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alpha_bininfo.R
import com.example.alpha_bininfo.data.HistoryItem

class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binNumber: TextView = itemView.findViewById(R.id.binNumber)
    private val cardType: TextView = itemView.findViewById(R.id.cardType)
    private val country: TextView = itemView.findViewById(R.id.countryName)
    private val coordinates: TextView = itemView.findViewById(R.id.coordinates)
    private val bankName: TextView = itemView.findViewById(R.id.bank_name)
    private val bankUrl: TextView = itemView.findViewById(R.id.bank_url)
    private val bankPhone: TextView = itemView.findViewById(R.id.bank_phone)

    @SuppressLint("SetTextI18n")
    fun bind(item: HistoryItem) {
        binNumber.text = "BIN: ${item.bin}"

        cardType.text = "Тип карты: ${item.info?.scheme ?: "-"}"
        country.text = "Страна:  ${item.info?.country?.name ?: "-"}"

        item.info?.country?.let {
            coordinates.text = "(${it.latitude ?: "-"}, ${it.longitude ?: "-"})"
        } ?: run {
            coordinates.text = "(-, -)"
        }

        bankName.text = "Название банка: \n${item.info?.bank?.name ?: "-"}"
        bankUrl.text = "url: ${item.info?.bank?.url ?: "-"}"
        bankPhone.text = "Номер телефона: ${item.info?.bank?.phone ?:  "-"}"
    }
}