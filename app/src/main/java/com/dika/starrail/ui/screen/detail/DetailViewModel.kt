package com.dika.starrail.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dika.starrail.data.local.CharacterEntity
import com.dicoding.gotrip.utils.UiState
import com.dika.starrail.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _Character = MutableStateFlow<UiState<CharacterEntity>>(UiState.Loading)
    val Character = _Character.asStateFlow()

    fun getCharacter(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCharacter(id)
                .catch { _Character.value = UiState.Error(it.message.toString()) }
                .collect { _Character.value = UiState.Success(it) }
        }
    }

    fun updateFavoriteCharacter(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavoriteCharacter(id, isFavorite)
        }
    }
}