package com.example.movieapp

import retrofit2.http.GET

interface CharactersService {
    @GET("character")
    suspend fun getCharacters(): CharactersResult
}
