package com.example.museoapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.museoapp.model.Artwork
import com.example.museoapp.network.ArticApi
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface MuseoUiState {
    data class Success(
        val artworks: List<Artwork>,
        val iiifUrl: String
    ) : MuseoUiState
    object Error : MuseoUiState
    object Loading : MuseoUiState
}

class MuseoViewModel : ViewModel() {

    var museoUiState: MuseoUiState by mutableStateOf(
        MuseoUiState.Loading
    )
        private set

    init {
        getArtworks()
    }

    private fun getArtworks() {
        viewModelScope.launch {
            museoUiState = try {
                val response = ArticApi.retrofitService.getArtworks()

                val artworksWithImages = response.data.filter {
                    !it.imageId.isNullOrBlank()
                }

                if (artworksWithImages.isNotEmpty()) {
                    MuseoUiState.Success(
                        artworks = artworksWithImages,
                        iiifUrl = response.config.iiifUrl
                    )
                } else {
                    MuseoUiState.Error
                }
            } catch (e: IOException) {
                MuseoUiState.Error
            } catch (e: Exception) {
                MuseoUiState.Error
            }
        }
    }
}
