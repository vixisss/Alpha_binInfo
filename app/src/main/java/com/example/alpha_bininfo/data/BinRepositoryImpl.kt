package com.example.alpha_bininfo.data

import com.example.alpha_bininfo.domain.BinRepository

class BinRepositoryImpl(
    private val apiService: BinApiService,
    private val historyStorage: HistoryStorage
) : BinRepository {
    override suspend fun getBinInfo(bin: String): Result<BinInfo> {
        return try {
            val response = apiService.getBinInfo(bin)
            historyStorage.addToHistory(bin, response)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getHistory(): List<HistoryItem> {
        return historyStorage.getHistory()
    }
}