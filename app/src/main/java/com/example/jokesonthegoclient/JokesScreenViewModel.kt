package com.example.jokesonthegoclient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.stephenbapple.jokesonthegoservice.GetAnyRandomJokeResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokesScreenViewModel @Inject constructor(
    private val rpcClient: JokeServiceClient
): ViewModel() {
    private val _jokeResponse = MutableStateFlow<GetAnyRandomJokeResponse?>(null)
    val jokeResponse: StateFlow<GetAnyRandomJokeResponse?> = _jokeResponse

    fun getRandomJoke() {
        viewModelScope.launch {
            val res = rpcClient.getRandomJoke()
            _jokeResponse.value = res
        }
    }
}
