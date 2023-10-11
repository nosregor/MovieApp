package com.example.movieapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Character
import com.example.movieapp.model.CharactersRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(charactersRepository: CharactersRepository) : ViewModel() {

    private val _state: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val state: StateFlow<UiState> = _state


    init {

        viewModelScope.launch {
            // change state var and set initial values followed by a 2 sec delay
            delay(2000)

            try {
                // calling the API
                val characters = charactersRepository.getAll()

                // check if the network call is successful
                if (characters.isNotEmpty()) {
                    val filteredCharacters =
                        characters.filter { it.name.contains("Smith") }
                    _state.value = UiState.Success(data = filteredCharacters)
                } else {
                    throw UnsupportedOperationException() //Exception("Something is wrong with the call")
                }

            } catch (e: Exception) {
                println(e)
                Log.e("ANDREW", e.javaClass.name)

                when (e) {
                    is UnsupportedOperationException -> _state.value =
                        UiState.Error("Problem with the server 500")

                    else -> _state.value = UiState.Error("Problem with the request 400")
                }
            }

        }
    }

    fun onCharacterClicked(character: Character) {
        // update the character list
        if (_state.value is UiState.Success) {
            val oldCharacters = state.value as UiState.Success
            val newCharacters = oldCharacters.data.map {
                if (it.name == character.name) {
                    it.copy(favorite = !it.favorite)
                } else {
                    it
                }
            }

            // update the private stated with updated character list
            _state.value = UiState.Success(newCharacters)
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val data: List<Character>) : UiState()
        data class Error(val message: String) : UiState()
    }

}
