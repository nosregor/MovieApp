package com.example.movieapp.model

import com.example.movieapp.model.datasources.ApiDataSource

class CharactersRepository(private val apiDataSource: ApiDataSource) {
    suspend fun getAll(): List<Character> {



        return apiDataSource.charactersService.getCharacters().body()?.results ?: emptyList()
    }
}
