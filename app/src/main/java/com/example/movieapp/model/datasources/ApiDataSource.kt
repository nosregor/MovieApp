package com.example.movieapp.model.datasources

import com.example.movieapp.network.CharactersService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiDataSource {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val charactersService: CharactersService = retrofit.create(CharactersService::class.java)
}

