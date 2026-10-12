package com.example.museoapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import com.example.museoapp.R
import com.example.museoapp.model.Artwork
import com.example.museoapp.viewmodel.MuseoUiState

@Composable
fun HomeScreen(
    museoUiState: MuseoUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (museoUiState) {
        is MuseoUiState.Loading -> {
            LoadingScreen(modifier = modifier.fillMaxSize())
        }

        is MuseoUiState.Success -> {
            PhotosGridScreen(
                artworks = museoUiState.artworks,
                iiifUrl = museoUiState.iiifUrl,
                modifier = modifier,
                contentPadding = contentPadding
            )
        }

        is MuseoUiState.Error -> {
            ErrorScreen(modifier = modifier.fillMaxSize())
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.loader),
            contentDescription = "Cargando obras de arte"
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error_load),
            contentDescription = "Error de conexión"
        )

        Text(
            text = stringResource(
                id = R.string.problem_with_connection
            )
        )
    }
}

@Composable
fun ArtworkCard(
    artwork: Artwork,
    iiifUrl: String,
    modifier: Modifier = Modifier
) {
    val imageUrl =
        "${iiifUrl.trimEnd('/')}/${artwork.imageId}/full/843,/0/default.jpg"

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(
                    context = LocalContext.current
                )
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = artwork.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                error = painterResource(R.drawable.error_404),
                placeholder = painterResource(R.drawable.carga),
                contentScale = ContentScale.Fit
            )

            Text(
                text = artwork.title,
                modifier = Modifier.padding(
                    start = 8.dp,
                    end = 8.dp,
                    top = 8.dp,
                    bottom = 4.dp
                )
            )

            artwork.artistDisplay?.let { artist ->
                Text(
                    text = artist,
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    )
                )
            }

            artwork.dateDisplay?.let { date ->
                Text(
                    text = date,
                    modifier = Modifier.padding(
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp
                    )
                )
            }
        }
    }
}

@Composable
fun PhotosGridScreen(
    artworks: List<Artwork>,
    iiifUrl: String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding
    ) {
        items(
            items = artworks,
            key = { artwork -> artwork.id }
        ) { artwork ->
            ArtworkCard(
                artwork = artwork,
                iiifUrl = iiifUrl,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
        }
    }
}

