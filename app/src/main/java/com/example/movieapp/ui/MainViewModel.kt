package com.example.movieapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Character
import com.example.movieapp.network.CharactersService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    private val _state: MutableStateFlow<UiState> = MutableStateFlow(UiState(error = null))
    val state: StateFlow<UiState> = _state


    init {
        viewModelScope.launch {
            // change state var and set initial values followed by a 2 sec delay
            _state.value = UiState(isLoading = true, characters = emptyList(), null)
            delay(2000)

            // retrofit instance
            val retofit = Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiClient = retofit.create(CharactersService::class.java)
            println(apiClient)
            println("Andrew")

            //calling API
            try {
                throw Exception("Hi There!")

                val characters = apiClient.getCharacters()
                    .results

                val filteredCharacters = characters.filter { it.name.contains("Smith") }

                _state.value = UiState( isLoading = false,
                    characters = filteredCharacters, null)
            } catch (e: Exception) {
                println(e)

                // 1. filter out the exception
                // catch app crash
//                when(e){
//                    is ConnectException -> //TODO: handle connection error
//                    is UnknownHostException ->  //TODO: handle no internet connection error
//                }

                _state.value = UiState( isLoading = false,
                    characters = emptyList(), e.message)
            }

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
        val error: String?
    )

}
