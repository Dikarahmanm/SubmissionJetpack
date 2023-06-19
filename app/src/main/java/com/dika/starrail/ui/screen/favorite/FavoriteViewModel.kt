package com.dika.starrail.ui.screen.favorite

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
class FavoriteViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _allFavoriteCharacter = MutableStateFlow<UiState<List<CharacterEntity>>>(UiState.Loading)
    val allFavoriteCharacter = _allFavoriteCharacter.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllFavoriteCharacter()
                .catch { _allFavoriteCharacter.value = UiState.Error(it.message.toString()) }
                .collect { _allFavoriteCharacter.value = UiState.Success(it) }
        }
    }

    fun updateFavoriteCharacter(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavoriteCharacter(id, isFavorite)
        }
    }
}