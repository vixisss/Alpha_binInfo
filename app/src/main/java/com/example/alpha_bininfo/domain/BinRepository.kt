package com.example.alpha_bininfo.domain

import com.example.alpha_bininfo.data.BinInfo
import com.example.alpha_bininfo.data.HistoryItem

interface BinRepository {
    suspend fun getBinInfo(bin: String): Result<BinInfo>
    fun getHistory(): List<HistoryItem>
}