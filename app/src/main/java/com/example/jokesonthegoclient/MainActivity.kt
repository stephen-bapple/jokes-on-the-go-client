package com.example.jokesonthegoclient

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val theJoke by viewModel.jokeResponse.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val hasError by viewModel.errored.collectAsState()
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val jokeText = if (theJoke == null && !hasError) {
            ">..."
        } else if (hasError) {
            "Error: Could not retrieve data from Joke Service"
        } else {
            "${theJoke?.joke?.setup}\n\n${theJoke?.joke?.punchline}"
        }
        JokeRetrieverCard(
            jokeText = jokeText,
            enabled = !isLoading,
            jokeUpdateFunc = { viewModel.getRandomJoke() })
    }
}

@Composable
fun JokeRetrieverCard(
    jokeText: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    jokeUpdateFunc: () -> Unit = {},
) {
    val metallicBlueSweep = listOf(
        Color(0xFFB0CFE5), // Light blue-gray
        Color(0xFF90B5BE), // Medium blue-gray
        Color(0xFF78A1AC), // Dark blue-gray
        Color(0xFFB0CFE5)  // Light blue-gray again to create the sweep effect
    )
    Column(
        modifier = modifier
            .background(brush = Brush.sweepGradient(metallicBlueSweep))
            .fillMaxSize()
            .shadow(4.dp, shape = RoundedCornerShape(16.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(.75f)
                .fillMaxHeight(0.30f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = jokeText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Green,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF252525), shape = RoundedCornerShape(16.dp))

                    .border(1.dp, Color.Black, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
                    .weight(1f),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = jokeUpdateFunc,
                enabled = enabled,
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(1.dp, Color(0xEE339685)),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.End)
                    .height(48.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 16.dp,
                    pressedElevation = 4.dp,
                    disabledElevation = 0.dp,
                    hoveredElevation = 8.dp,
                    focusedElevation = 8.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF43B6A5),
                    contentColor = Color.White
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (enabled) {
                        Text(
                            text = "Get Random Joke",
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        CircularProgressIndicator(
                            color = Color.Green,
                            modifier = Modifier
                                .height(32.dp)
                                .width(32.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewJokeRetrieverCard() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val aJoke = "Why did the user cross the screen?\n" +
                "\n" +
                "To get to the other slide"
        JokeRetrieverCard(
            jokeText = aJoke,
            enabled = true
        ) {}
    }
}