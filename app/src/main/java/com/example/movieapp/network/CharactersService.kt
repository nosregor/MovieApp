package com.example.movieapp.network

import com.example.movieapp.model.CharactersResult
import retrofit2.http.GET

interface CharactersService {
    @GET("character")
    suspend fun getCharacters(): CharactersResult
}
