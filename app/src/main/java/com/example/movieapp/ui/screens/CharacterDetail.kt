package com.example.movieapp.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.movieapp.model.CharactersRepository
import com.example.movieapp.ui.composables.MyScaffold

@Composable
fun CharacterDetail(
    repository: CharactersRepository,
    characterId: Int?,
    onBack: () -> Unit,
) {

    MyScaffold("Character Detail", onBackClick = onBack, content = { innerPadding ->

        BackHandler {
            onBack()
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Character Id: $characterId",
                textAlign = TextAlign.Center
            )
        }
    })
}
