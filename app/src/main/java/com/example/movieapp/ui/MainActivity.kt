package com.example.movieapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.movieapp.model.CharactersRepository
import com.example.movieapp.model.datasources.ApiDataSource
import com.example.movieapp.ui.screens.Home
import com.example.movieapp.usecases.FilterCharactersByLastNameUseCase

class MainActivity : ComponentActivity() {
    @SuppressLint("ProduceStateDoesNotAssignValue")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate()")

        val repository = CharactersRepository(ApiDataSource())
        val filterUseCase = FilterCharactersByLastNameUseCase(repository)

        setContent {
            Home(filterUseCase)
        }
    }

    override fun onStart() {
        super.onStart()
        println("onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("onResume()")
    }

    override fun onPause() {
        super.onPause()
        println("onPause()")
    }

    override fun onStop() {
        super.onStop()
        println("onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        println(onDestroy())
    }
}
