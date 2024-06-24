package com.tobiapplications.kmpmeetup.android.ui.jokes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tobiapplications.kmpmeetup.android.ui.composables.JokeText
import com.tobiapplications.kmpmeetup.android.ui.composables.KMPAppBar
import com.tobiapplications.kmpmeetup.android.utils.KMPTheme
import com.tobiapplications.kmpmeetup.android.utils.ThemePreviews
import com.tobiapplications.kmpmeetup.domainlayer.model.Joke

@Composable
fun JokesScreen(
    jokesUiState: JokesUiState,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            KMPAppBar(
                text = "Jokes",
                onBackButtonClicked = onNavigateBack
            )
        },
        modifier = Modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(all = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (jokesUiState) {
                is JokesUiState.Loading -> CircularProgressIndicator()
                is JokesUiState.Data -> {
                    JokeText(joke = jokesUiState.joke)
                }
            }
        }
    }
}


@ThemePreviews
@Composable
fun DefaultPreview() {
    KMPTheme {
        JokesScreen(
            jokesUiState = JokesUiState.Data(
                joke = Joke(
                    question = "",
                    answer = "",
                    jokeText = "Joke"
                )
            ),
            onNavigateBack = {}
        )
    }
}