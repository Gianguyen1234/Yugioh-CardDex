package com.example.ygocarddex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ygocarddex.data.api.RetrofitInstance
import com.example.ygocarddex.data.models.Card
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    object Loading : UiState()
    data class Success(val cards: List<Card>) : UiState()
    data class Error(val message: String) : UiState()
}

class YugiohViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        fetchCards()
    }

    private fun fetchCards() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCards()
                _uiState.value = UiState.Success(response.data)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to load cards: ${e.localizedMessage}")
            }
        }
    }
}