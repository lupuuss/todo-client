package com.github.lupuuss.todo.client.core.auth

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.util.pipeline.*

class AuthRequiredException : Exception("First token must be provided manually to TokenHolder!")

class JwtAuth(configure: Configuration) {

    private val authClient: HttpClient = requireNotNull(configure.authClient)
    private val refreshPath: String = requireNotNull(configure.refreshUrl)
    private val tokenHolder: TokenHolder = requireNotNull(configure.tokenHolder)

    private val retryFlag = AttributeKey<Unit>("RetryFlag")

    class Configuration {
        var tokenHolder: TokenHolder? = null
        var authClient: HttpClient? = null
        var refreshUrl: String? = null
    }

    fun interceptRequestPipeline(pipeline: HttpRequestPipeline) {

        val phase = PipelinePhase("ApplyJwtToken")

        pipeline.insertPhaseBefore(HttpRequestPipeline.Before, phase)

        pipeline.intercept(phase) {

            val token = tokenHolder.getToken() ?: throw AuthRequiredException()

            context.header("Authorization", "Bearer $token")
        }
    }

    fun interceptReceivePipeline(pipeline: HttpReceivePipeline) {
        val phase = PipelinePhase("RefreshTokenIfUnauthorized")

        pipeline.insertPhaseAfter(HttpReceivePipeline.After, phase)

        pipeline.intercept(phase) {

            if (context.response.status != HttpStatusCode.Unauthorized) return@intercept

            if (context.attributes.contains(retryFlag)) return@intercept

            val token = tokenHolder.getToken() ?: return@intercept

            val newToken = authClient.post<String>(refreshPath) {
                header("Authorization", "Bearer $token")
            }

            tokenHolder.setToken(newToken)

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
