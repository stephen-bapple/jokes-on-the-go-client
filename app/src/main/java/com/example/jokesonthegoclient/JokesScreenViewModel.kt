package com.example.jokesonthegoclient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokesScreenViewModel @Inject constructor(): ViewModel() {
    private val _jokeResponse = MutableStateFlow<String?>("")
    val jokeResponse: StateFlow<String?> = _jokeResponse

    fun getRandomJoke() {
        viewModelScope.launch {

        }
    }
}
