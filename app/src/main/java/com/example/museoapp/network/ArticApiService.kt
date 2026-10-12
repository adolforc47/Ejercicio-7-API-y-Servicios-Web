package com.example.museoapp.network

import com.example.museoapp.model.ArtworksResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.artic.edu/api/v1/"

private val json = Json {
    ignoreUnknownKeys = true
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(
        json.asConverterFactory(
            "application/json".toMediaType()
        )
    )
    .baseUrl(BASE_URL)
    .build()

interface ArticApiService {

    @GET("artworks")
    suspend fun getArtworks(
        @Query("limit") limit: Int = 10,
        @Query("fields") fields: String =
            "id,title,artist_display,date_display,image_id"
    ): ArtworksResponse
}

object ArticApi {
    val retrofitService: ArticApiService by lazy {
        retrofit.create(ArticApiService::class.java)
    }
}