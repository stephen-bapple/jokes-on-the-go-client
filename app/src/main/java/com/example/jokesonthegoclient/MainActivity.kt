package com.example.jokesonthegoclient

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jokesonthegoclient.ui.theme.JokesOnTheGoClientTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val jokesScreenViewModel: JokesScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JokesOnTheGoClientTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    JokesScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = jokesScreenViewModel,
                    )
                }
            }
        }
    }
}

@Composable
fun JokesScreen(viewModel: JokesScreenViewModel, modifier: Modifier = Modifier) {
    val theJoke = viewModel.jokeResponse.collectAsState()
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val jokeText = if (theJoke.value == null) {
            "Press the button!"
        } else {
            "${theJoke.value?.joke?.setup}\n\n${theJoke.value?.joke?.punchline}"
        }
        JokeRetrieverCard(
            jokeText = jokeText,
            jokeUpdateFunc = { viewModel.getRandomJoke() })
    }
}

@Composable
fun JokeRetrieverCard(
    jokeText: String,
    modifier: Modifier = Modifier,
    jokeUpdateFunc: () -> Unit
) {
    Column(
        modifier = modifier
            .background(color = Color.DarkGray)
            .fillMaxSize(.75f)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = jokeText
        )
        Button(onClick = jokeUpdateFunc) {
            Text(
                text="Get random joke"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewJokeRetrieverCard() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        JokeRetrieverCard(jokeText = "Why did the user cross the screen?\n\nTo get to the other slide") {}
    }
}