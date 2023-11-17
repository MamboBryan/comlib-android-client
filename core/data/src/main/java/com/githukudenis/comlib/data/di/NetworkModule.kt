package com.githukudenis.comlib.data.di

import android.util.Log
import com.githukudenis.comlib.core.network.UserApi
import com.githukudenis.comlib.data.repository.AuthRepository
import com.githukudenis.comlib.data.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.DefaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import timber.log.Timber

private const val TIMEOUT = 60_000

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        val httpClient = HttpClient(Android) {

            install(JsonFeature) {
                serializer = KotlinxSerializer(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })

                engine {
                    connectTimeout = TIMEOUT
                    socketTimeout = TIMEOUT
                }
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.tag("Logger Ktor => ").v(message)
                    }
                }
                level = LogLevel.NONE
            }

            install(ResponseObserver) {
                onResponse { response ->
                    Timber.tag("HTTP status: ").d("${response.status.value}")
                }
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
        return httpClient
    }
}