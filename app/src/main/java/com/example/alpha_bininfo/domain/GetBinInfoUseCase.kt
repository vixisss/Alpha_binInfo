package com.example.alpha_bininfo.domain

import com.example.alpha_bininfo.data.BinInfo
import com.example.alpha_bininfo.data.HistoryItem

class GetBinInfoUseCase(private val repository: BinRepository) {
    suspend operator fun invoke(bin: String): Result<BinInfo> {
        return repository.getBinInfo(bin)
    }

    fun getHistory(): List<HistoryItem> {
        return repository.getHistory()
    }
}