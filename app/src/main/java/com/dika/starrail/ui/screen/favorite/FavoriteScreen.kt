package com.dika.starrail.ui.screen.favorite

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dika.starrail.data.local.CharacterEntity
import com.dicoding.gotrip.utils.UiState
import com.dika.starrail.ui.components.AvailableContent
import com.dika.starrail.ui.components.CustomEmptyContent
import com.dika.starrail.ui.components.CustomErrorContent
import com.dika.starrail.ui.components.CustomLoadingIndicator

@Composable
fun FavoriteScreen(navController: NavController, scaffoldState: ScaffoldState) {
    val favoriteViewModel = hiltViewModel<FavoriteViewModel>()

    favoriteViewModel.allFavoriteCharacter.collectAsState(UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> CustomLoadingIndicator()
            is UiState.Error -> CustomErrorContent()
            is UiState.Success -> {
                FavoriteContent(
                    listFavoriteCharacter = uiState.data,
                    navController = navController,
                    scaffoldState = scaffoldState,
                    onUpdateFavoriteCharacter = favoriteViewModel::updateFavoriteCharacter
                )
            }
        }
    }
}

@Composable
fun FavoriteContent(
    listFavoriteCharacter: List<CharacterEntity>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoriteCharacter: (id: Int, isFavorite: Boolean) -> Unit
) {
    when (listFavoriteCharacter.isEmpty()) {
        true -> CustomEmptyContent()
        false -> AvailableContent(listFavoriteCharacter, navController, scaffoldState, onUpdateFavoriteCharacter)
    }
}