package com.dika.starrail.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

import com.dika.starrail.data.local.CharacterEntity
import com.dika.starrail.ui.components.CustomErrorContent
import com.dicoding.gotrip.utils.UiState
import com.dicoding.gotrip.utils.compactDecimalFormat
import com.dicoding.gotrip.utils.countStar
import com.dika.starrail.R
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(CharacterId: Int, navController: NavController, scaffoldState: ScaffoldState) {
    val detailViewModel = hiltViewModel<DetailViewModel>()
    detailViewModel.Character.collectAsState(UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> detailViewModel.getCharacter(CharacterId)
            is UiState.Error -> CustomErrorContent()
            is UiState.Success -> {
                DetailContent(
                    uiState.data,
                    navController,
                    scaffoldState,
                    detailViewModel::updateFavoriteCharacter
                )
            }
        }
    }
}@Composable
fun DetailContent(
    Character: CharacterEntity,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoriteCharacter: (id: Int, isFavorite: Boolean) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val (id, name, description, location, photoUrl, rating, path, isFavorite) = Character

    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            Box {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.img),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
                Image(
                    painter = painterResource(id = path),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp).align(Alignment.BottomEnd).padding(end = 10.dp),// Occupy the entire Box
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topEnd = 8.dp))
                        .background(androidx.compose.material3.MaterialTheme.colorScheme.primary,)
                        .align(Alignment.BottomStart),
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(
                            text = location,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.h6
                    )

                    Icon(
                        imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                        tint = if (isFavorite) Color.Red else MaterialTheme.colors.onSurface,
                        contentDescription = name,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(100))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onUpdateFavoriteCharacter(id ?: 0, !isFavorite)
                                coroutineScope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "$name ${if (isFavorite) "removed from" else "added as"} favorite ",
                                        actionLabel = "Dismiss",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            },
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    val nstar = rating.toInt()
                    repeat(nstar) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = null,
                            tint = Color(0xFFE5C558),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.body1,
                    lineHeight = 24.sp,
                )

                Spacer(modifier = Modifier.height(16.dp)) // Tambahkan spacer untuk memisahkan deskripsi dengan elemen berikutnya

                Divider(color = MaterialTheme.colors.onSurface, modifier = Modifier.padding(vertical = 8.dp)) // Tambahkan garis pemisah

                // Tambahkan elemen lain sesuai kebutuhan, misalnya text, spacer, atau box
            }
        }

        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp)
                .align(Alignment.TopStart)
                .clip(CircleShape)
                .size(40.dp)
                .background(Color.White)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
            )
        }
    }
}


