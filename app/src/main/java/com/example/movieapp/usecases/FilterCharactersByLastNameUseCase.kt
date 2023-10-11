package com.example.movieapp.usecases
import com.example.movieapp.model.Character
import com.example.movieapp.model.CharactersRepository

class FilterCharactersByLastNameUseCase(private val repository: CharactersRepository) {

    suspend operator fun invoke(name: String): List<Character> {
        val list = repository.getAll()
        return list.filter { it.name.contains(name) }
    }
}
