# Jokes on the Go Android Client
This repo is an Android application that uses Jetpack Compose for its UI and Kotlin gRPC stubs to
retrieve data from the joke server.

Currently there is not much functionality. The app is a single screen in which you can request a 
random joke by pressing a button. However, even this leverages Jetpack Compose and Kotlin's 
capabilities in a notable way; I found it incredibly easy to add event hooks and update state, just as easy as React.

The code is entirely in app/src/main/java/com/example/jokesonthegoclient 
(placeholder package will be replaced later) and includes the main activity (MainActivity.kt), 
a view model for the joke retriever that leverages gRPC (JokesScreenViewModel.kt), 
and a RPC module that leverages Hilt+Dagger to inject the RPC client as a singleton (RpcModule.kt).  

## Demo
![App and server interacting](./project-in-action.gif)

## Server
The server is entirely separate and coded in Golang.  
Go to https://github.com/stephen-bapple/jokes-on-the-go for the server and protobuf stubs.
