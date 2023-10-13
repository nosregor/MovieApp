package com.example.movieapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.movieapp.ui.navigation.Navigation

class MainActivity : ComponentActivity() {
    @SuppressLint("ProduceStateDoesNotAssignValue")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate()")

//        val filterUseCase = FilterCharactersByLastNameUseCase(repository)

        setContent {
            Navigation()
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
