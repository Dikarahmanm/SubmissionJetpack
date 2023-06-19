package com.dika.starrail.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dika.starrail.data.local.CharacterEntity
import com.dika.starrail.utils.CharacterData
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
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _allCharacter = MutableStateFlow<UiState<List<CharacterEntity>>>(UiState.Loading)
    val allCharacter = _allCharacter.asStateFlow()

    private val _homeState = mutableStateOf(HomeState())
    val homeState: State<HomeState> = _homeState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllCharacter().collect { Character ->
                when (Character.isEmpty()) {
                    true -> repository.insertAllCharacter(CharacterData.dummy)
                    else -> _allCharacter.value = UiState.Success(Character)
                }
            }
        }
    }

    private fun searchCharacter(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchCharacter(query)
                .catch { _allCharacter.value = UiState.Error(it.message.toString()) }
                .collect { _allCharacter.value = UiState.Success(it) }
        }
    }

    fun updateFavoriteCharacter(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavoriteCharacter(id, isFavorite)
        }
    }

    fun onQueryChange(query: String) {
        _homeState.value = _homeState.value.copy(query = query)
        searchCharacter(query)
    }
}