package com.example.movieapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Character
import com.example.movieapp.network.CharactersService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    private val _state: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    init {
        viewModelScope.launch {
            _state.value = UiState(isLoading = true, characters = emptyList())
            delay(2000)
            _state.value = UiState( isLoading = false,
                characters = Retrofit.Builder()
                    .baseUrl("https://rickandmortyapi.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(CharactersService::class.java)
                    .getCharacters()
                    .results
                    .filter { it.name.contains("Smith") }
            )
        }
    }

    fun onCharacterClicked(character: Character) {
        _state.value = _state.value.copy(
            characters = _state.value.characters.map {
                if (it.name == character.name) {
                    it.copy(favorite = !it.favorite)
                } else {
                    it
                }
            }
        )
    }

    data class UiState(
        val isLoading: Boolean = false,
        val characters: List<Character> = emptyList(),
    )

}
