package com.example.movieapp.model.datasources

import com.example.movieapp.model.Character
import com.example.movieapp.network.CharactersService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiDataSource {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val charactersService: CharactersService = retrofit.create(CharactersService::class.java)

    suspend fun getAllCharacters(): List<Character> {
        return charactersService.getCharacters().body()?.results ?: emptyList()
    }

    suspend fun getCharacter(id: Int): Character? {
        return charactersService.getCharacter(id).body()
    }
}
