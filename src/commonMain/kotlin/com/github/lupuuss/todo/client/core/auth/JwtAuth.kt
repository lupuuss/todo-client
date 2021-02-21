package com.github.lupuuss.todo.client.core.auth

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

typealias TokenProvider = suspend () -> String?
typealias TokenRefreshCallback = suspend () -> Unit

class JwtAuth(configure: Configuration) {

    private val tokenProvider: TokenProvider = requireNotNull(configure.tokenProvider)
    private val refreshCallback: TokenRefreshCallback = requireNotNull(configure.tokenRefreshCallback)

    private val retryFlag = AttributeKey<Unit>("RetryFlag")

    private val refreshLock = Mutex()

    class Configuration {
        var tokenProvider: TokenProvider? = null
        var tokenRefreshCallback: TokenRefreshCallback? = null
    }

    fun interceptRequestPipeline(pipeline: HttpRequestPipeline) {

        val phase = PipelinePhase("ApplyJwtToken")

        pipeline.insertPhaseBefore(HttpRequestPipeline.Before, phase)

        pipeline.intercept(phase) {

            val token = tokenProvider() ?: throw AuthRequiredException()

            context.header("Authorization", "Bearer $token")
        }
    }

    fun interceptReceivePipeline(pipeline: HttpReceivePipeline) {
        val phase = PipelinePhase("RefreshTokenIfUnauthorized")

        pipeline.insertPhaseAfter(HttpReceivePipeline.After, phase)

        pipeline.intercept(phase) {

            if (context.response.status != HttpStatusCode.Unauthorized) return@intercept

            if (context.attributes.contains(retryFlag)) return@intercept

            refreshCallback()

            val httpBuilder = HttpRequestBuilder().takeFrom(context.request)

            // removes old Authorization
            httpBuilder.headers.remove("Authorization")

            httpBuilder.setAttributes {
                put(retryFlag, Unit)
            }

            val response = context.client!!.get<HttpResponse>(httpBuilder)
            proceedWith(response)
        }
    }

    companion object Feature : HttpClientFeature<Configuration, JwtAuth> {

        override val key: AttributeKey<JwtAuth> = AttributeKey("JwtAuth")

        override fun install(feature: JwtAuth, scope: HttpClient) {
            feature.interceptRequestPipeline(scope.requestPipeline)
            feature.interceptReceivePipeline(scope.receivePipeline)
        }

        override fun prepare(block: Configuration.() -> Unit): JwtAuth {
            return JwtAuth(Configuration().apply(block))
        }
    }
}
