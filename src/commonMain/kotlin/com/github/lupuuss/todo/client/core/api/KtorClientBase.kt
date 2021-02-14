package com.github.lupuuss.todo.client.core.api

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

open class KtorClientBase(protected val baseUrl: String, protected val client: HttpClient) {

    protected fun String.applyParameters(params: Map<String, Any?>? = null): String {
        val fParams = params?.filter { (_, value) -> value != null } ?: return this

        if (fParams.isEmpty()) return this

        val strBuilder = StringBuilder(this)

        val (fName, fValue) = fParams.entries.first()

        strBuilder.append("?$fName=$fValue")

        for ((name, value) in fParams.entries.drop(1)) {

            strBuilder.append('&')
            strBuilder.append(name)
            strBuilder.append('=')
            strBuilder.append(value)
        }

        return strBuilder.toString()
    }

    protected fun getUrl(relativePath: String)  = baseUrl + relativePath

    protected suspend inline fun <reified T> get(path: String, parameters: Map<String, Any?>? = null): T {

        val url = getUrl(path)
        return client.get(url.applyParameters(parameters))
    }

    protected suspend inline fun <reified T> postJson(path: String, body: Any): T {
        return client.post(getUrl(path)) {

            contentType(ContentType.parse("application/json"))
            this.body = body
        }
    }
}