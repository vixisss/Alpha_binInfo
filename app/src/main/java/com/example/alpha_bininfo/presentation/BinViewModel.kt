package com.example.alpha_bininfo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpha_bininfo.data.BinInfo
import com.example.alpha_bininfo.data.HistoryItem
import com.example.alpha_bininfo.domain.GetBinInfoUseCase
import kotlinx.coroutines.launch

class BinViewModel(
    private val getBinInfoUseCase: GetBinInfoUseCase
) : ViewModel() {
    private val _binInfo = MutableLiveData<BinInfo?>()
    val binInfo: LiveData<BinInfo?> = _binInfo

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _history = MutableLiveData<List<HistoryItem>>()
    val history: LiveData<List<HistoryItem>> = _history

    fun loadHistory() {
        _history.value = getBinInfoUseCase.getHistory()
    }

    fun fetchBinInfo(bin: String) {
        viewModelScope.launch {
            _loading.value = true
            getBinInfoUseCase(bin)
                .onSuccess {
                    _binInfo.value = it
                    loadHistory()
                }
                .onFailure {
                    _error.value = it.message
                }
            _loading.value = false
        }
    }
}