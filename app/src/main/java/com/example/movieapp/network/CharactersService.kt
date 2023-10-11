package com.example.movieapp.network

import com.example.movieapp.model.Character
import com.example.movieapp.model.CharactersResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CharactersService {
    @GET("character")
    suspend fun getCharacters(): Response<CharactersResult>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") characterId: Int): Response<Character>
}
