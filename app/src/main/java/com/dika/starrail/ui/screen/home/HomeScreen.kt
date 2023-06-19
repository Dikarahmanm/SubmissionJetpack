package com.dika.starrail.ui.screen.home

import SearchBar
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dika.starrail.data.local.CharacterEntity
import com.dicoding.gotrip.utils.UiState
import com.dika.starrail.ui.components.AvailableContent
import com.dika.starrail.ui.components.CustomEmptyContent
import com.dika.starrail.ui.components.CustomErrorContent
import com.dika.starrail.ui.components.CustomLoadingIndicator

@Composable
fun HomeScreen(navController: NavController, scaffoldState: ScaffoldState) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val homeState by homeViewModel.homeState

    homeViewModel.allCharacter.collectAsState(UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> CustomLoadingIndicator()
            is UiState.Error -> CustomErrorContent()
            is UiState.Success -> {
                HomeContent(
                    listCharacter = uiState.data,
                    navController = navController,
                    scaffoldState = scaffoldState,
                    query = homeState.query,
                    onQueryChange = homeViewModel::onQueryChange,
                    onUpdateFavoriteCharacter = homeViewModel::updateFavoriteCharacter
                )
            }
        }
    }
}

@Composable
fun HomeContent(
    listCharacter: List<CharacterEntity>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    query: String,
    onQueryChange: (String) -> Unit,
    onUpdateFavoriteCharacter: (id: Int, isFavorite: Boolean) -> Unit
) {
    Column {
        SearchBar(query = query, onQueryChange = onQueryChange)
        when (listCharacter.isEmpty()) {
            true -> CustomEmptyContent()
            false -> AvailableContent(listCharacter, navController, scaffoldState, onUpdateFavoriteCharacter)
        }
    }
}

