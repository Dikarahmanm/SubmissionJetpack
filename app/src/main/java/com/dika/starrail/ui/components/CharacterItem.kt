package com.dika.starrail.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dika.starrail.data.local.CharacterEntity
import com.dika.starrail.ui.navigation.Screen
import com.dika.starrail.R
import com.dika.starrail.utils.CharacterData
import kotlinx.coroutines.launch

@Composable
fun AvailableContent(
    listCharacter: List<CharacterEntity>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoriteCharacter: (id: Int, isFavorite: Boolean) -> Unit,
) {
    LazyColumn {
        items(listCharacter, key = { it.name }) { Character ->
            CharacterItem(Character, navController, scaffoldState, onUpdateFavoriteCharacter)
        }
    }
}@Composable
fun CharacterItem(
    Character: CharacterEntity,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoriteCharacter: (id: Int, isFavorite: Boolean) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val (id, name, _, location, photoUrl, rating, path, isFavorite) = Character

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .border(1.dp, Color.LightGray.copy(0.5f), MaterialTheme.shapes.medium)
            .clickable { navController.navigate(Screen.Detail.createRoute(id ?: 0)) },
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(Color.LightGray)
            ) {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    placeholder = painterResource(R.drawable.img),
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.h6
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val nstar = rating.toInt()
                    repeat(nstar) {
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = null,
                            tint = Color(0xFFE5C558),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = path),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = location,
                    style = MaterialTheme.typography.body2
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(
                onClick = {
                    onUpdateFavoriteCharacter(id ?: 0, !isFavorite)
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "$name ${if (isFavorite) "removed from" else "added as"} favorite ",
                            actionLabel = "Dismiss",
                            duration = SnackbarDuration.Short
                        )
                    }
                },
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(100))
                    .background(
                        color = if (isFavorite) Color.Red else MaterialTheme.colors.surface,
                        shape = RoundedCornerShape(100)
                    ),
                content = {
                    Icon(
                        imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                        tint = if (isFavorite) Color.White else MaterialTheme.colors.onSurface,
                        contentDescription = name
                    )
                }
            )
        }
    }
}


@Preview
@Composable
fun PreviewCharacterItem() {
    val characters = CharacterData.dummy

    AvailableContent(
        listCharacter = characters,
        navController = NavController(LocalContext.current),
        scaffoldState = rememberScaffoldState(),
        onUpdateFavoriteCharacter = { id, isFavorite ->
            // Logika pembaruan karakter favorit
        }
    )
}

