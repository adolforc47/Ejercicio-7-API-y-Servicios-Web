package com.example.museoapp.model

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Artwork(
    val id: Int,
    val title: String,
    @SerialName("artist_display")
    val artistDisplay: String? = null,
    @SerialName("date_display")
    val dateDisplay: String? = null,
    @SerialName("image_id")
    val imageId: String? = null
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class ArtworksResponse(
    val data: List<Artwork>,
    val config: ApiConfig
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class ApiConfig(
    @SerialName("iiif_url")
    val iiifUrl: String
)




