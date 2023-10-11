package com.example.movieapp.model

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val favorite: Boolean = false,
)
