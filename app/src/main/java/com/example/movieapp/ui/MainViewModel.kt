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

//class MainViewModel(private val filterCharactersUseCase: FilterCharactersByLastNameUseCase) :
class MainViewModel(charactersRepository: CharactersRepository) :
    ViewModel() {

    private val _state: MutableStateFlow<UiState> = MutableStateFlow(UiState.Initial)
    val state: StateFlow<UiState> = _state

    fun onButtonClicked(charactersRepository: CharactersRepository) {
        // Start loading
        _state.value = UiState.Loading

        viewModelScope.launch {
            // change state var and set initial values followed by a 2 sec delay
            delay(2000)

            try {
//                val characters = filterCharactersUseCase("Smith")
                val characters = charactersRepository.getAll()
                println(characters)
                // check if the network call is successful
                if (characters.isNotEmpty()) {
                    _state.value = UiState.Success(data = characters)
                } else {
                    throw UnsupportedOperationException() //Exception("Something is wrong with the call")
                }

            } catch (e: Exception) {
                println(e)
                Log.e("Exception name: ", e.javaClass.name)

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
        object Initial : UiState()
        object Loading : UiState()
        data class Success(val data: List<Character>) : UiState()
        data class Error(val message: String) : UiState()
    }

}
