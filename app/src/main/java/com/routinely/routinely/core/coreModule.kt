package com.routinely.routinely.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.routinely.routinely.BuildConfig
import com.routinely.routinely.data.auth.HttpRoutes
import com.routinely.routinely.core.useCase.LogoutUseCase
import com.routinely.routinely.core.useCase.LogoutUseCaseImpl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.encodedPath
import io.ktor.serialization.gson.gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {
    single {
        provideHttpClient(get(), get())
    }

    single<ChuckerInterceptor> {
        provideChuckerInterceptor(get())
    }

    single<HttpClientEngine> {
        OkHttp.create {
            addNetworkInterceptor(get<ChuckerInterceptor>())
        }
    }

    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
            produceFile = { androidContext().preferencesDataStoreFile(Session.DATA) })
    }

    single<Session> {
        Session(get())
    }

    single<LogoutUseCase> {
        LogoutUseCaseImpl(get(), get())
    }
}

private fun provideChuckerInterceptor(context: Context) = ChuckerInterceptor.Builder(context)
    .collector(
        ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
    )
    .maxContentLength(250000L)
    .redactHeaders(emptySet())
    .alwaysReadResponseBody(false)
    .build()

private fun provideHttpClient(httpClientEngine: HttpClientEngine, session: Session) = HttpClient(httpClientEngine) {

    defaultRequest {
        url(BuildConfig.BASE_URL_DEBUG)
    }

    install(ContentNegotiation) {
        gson {
            serializeNulls()
            setPrettyPrinting()
        }
    }

    if (BuildConfig.DEBUG) {
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }
    }
    install(Auth) {
        bearer {
            sendWithoutRequest { request ->
                !request.url.encodedPath.startsWith("/auth")
            }
            loadTokens {
                BearerTokens(session.getToken(), session.getRefreshToken())
            }
            refreshTokens {
                client.post(HttpRoutes.REFRESH_TOKEN) {
                    markAsRefreshTokenRequest()
                    setBody(RefreshTokenBody(session.getRefreshToken()))
                }.toBearerTokens()
            }
        }
    }
    install(HttpTimeout)
}


suspend fun HttpResponse.toBearerTokens(): BearerTokens {
    val response = this.body<TokenResponse>()
    return BearerTokens(response.token, response.refreshToken)
}

data class RefreshTokenBody(
    val refreshToken: String?
)

data class TokenResponse(
    val token: String,
    val refreshToken: String,
    val expiresIn: String,
)