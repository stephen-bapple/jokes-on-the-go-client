package com.example.jokesonthegoclient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.stephenbapple.jokesonthegoservice.GetAnyRandomJokeResponse
import kotlinx.coroutines.delay
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
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _errored = MutableStateFlow(false)
    val errored: StateFlow<Boolean> = _errored

    fun getRandomJoke() {
        viewModelScope.launch {
            _errored.value = false
            try {
                _isLoading.value = true
                val res = rpcClient.getRandomJoke()
                delay(500)

                _jokeResponse.value = res
            } catch (e: Exception) {
                _jokeResponse.value = null
                _errored.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }
}
