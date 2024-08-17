package com.example.jokesonthegoclient

//
////import io.github.stephenbapple.jokesonthegoservice.JokeServiceGrpcKt.JokeServiceCoroutineStub
////import io.github.stephen_bapple.jokesonthegoservice.getAnyRandomJokeRequest
////import io.github.stephen_bapple.jokesonthegoservice.GetAnyRandomJokeResponse
//import androidx.annotation.Size
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import java.io.Closeable
//import java.util.concurrent.TimeUnit
//import javax.inject.Singleton
//
//class JokeServiceClient(private val channel: ManagedChannel, private val apiKey: String) :
//    Closeable {
//    private val stub: JokeServiceCoroutineStub = run {
//        val header = Metadata()
//        val authorizationKey = Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER)
//        header.put(authorizationKey, "Bearer $apiKey")
//
//        JokeServiceCoroutineStub(channel).withInterceptors(
//            MetadataUtils.newAttachHeadersInterceptor(header)
//        )
//    }
//
//    suspend fun getRandomJoke(): GetAnyRandomJokeResponse {
//        val request = getAnyRandomJokeRequest {}
//        val response = stub.getAnyRandomJoke(request)
//        return response
//    }
//
//    override fun close() {
//        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
//    }
//}
//
//@Module
//@InstallIn(SingletonComponent::class)
//object RpcModule {
//    @Provides
//    @Singleton
//    fun provideRpcClient(): String {
//        return ""
//    }
//}