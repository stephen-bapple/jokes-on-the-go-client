package com.example.jokesonthegoclient

import io.github.stephenbapple.jokesonthegoservice.JokeServiceGrpcKt.JokeServiceCoroutineStub
import io.github.stephenbapple.jokesonthegoservice.getAnyRandomJokeRequest
import io.github.stephenbapple.jokesonthegoservice.GetAnyRandomJokeResponse
import io.grpc.ManagedChannel
import io.grpc.Metadata
import io.grpc.stub.MetadataUtils
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.grpc.okhttp.OkHttpChannelBuilder
import java.io.Closeable
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

class JokeServiceClient(private val channel: ManagedChannel, private val apiKey: String) :
    Closeable {
    private val stub: JokeServiceCoroutineStub = run {
        val header = Metadata()
        val authorizationKey = Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER)
        header.put(authorizationKey, "Bearer $apiKey")

        JokeServiceCoroutineStub(channel).withInterceptors(
            MetadataUtils.newAttachHeadersInterceptor(header)
        )
    }

    suspend fun getRandomJoke(): GetAnyRandomJokeResponse {
        val request = getAnyRandomJokeRequest {}
        val response = stub.getAnyRandomJoke(request)
        return response
    }

    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RpcModule {
    @Provides
    @Singleton
    fun provideRpcClient(@ApplicationContext context: Context): JokeServiceClient {
        val apiKey = context.resources.getString(R.string.testing_api_key)
        val channel = OkHttpChannelBuilder
            .forAddress("jokesonthego.com", 50051)
            .useTransportSecurity()
            .build()
        return JokeServiceClient(channel, apiKey)
    }
}