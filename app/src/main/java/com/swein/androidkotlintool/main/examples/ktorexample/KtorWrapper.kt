package com.swein.androidkotlintool.main.examples.ktorexample

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import java.io.File

data class FormDataFile(
    val fileKey: String,
    val filePath: String,
    val fileName: String
)

object KtorWrapper {

    private var client: HttpClient? = null

    var onLog: ((message: String) -> Unit)? = null

    init {

        createClient()

    }

    private fun createClient() {

        client = HttpClient(Android) {

            install(Logging) {

                logger = object : Logger {
                    override fun log(message: String) {
                        onLog?.let {
                            it(message)
                        }
                    }
                }
                level = LogLevel.ALL
            }

//            install(JsonFeature) {
//                serializer = GsonSerializer()
//                acceptContentTypes = acceptContentTypes + ContentType.Application.Json
//            }

            engine {
                connectTimeout = 100_000
                socketTimeout = 100_000
            }

            HttpResponseValidator {

                handleResponseExceptionWithRequest { throwable, request ->

                    onLog?.let { onLog ->
                        throwable.message?.let { message ->
                            onLog(message)
                        }
                    }
                }
            }
        }
    }

    fun releaseClient() {
        client?.close()
        client = null
    }

    suspend fun get(
        url: String,
        headerMap: MutableMap<String, String>? = null,
        queryParameterMap: MutableMap<String, String>? = null
    ): HttpResponse {

        var urlString = url

        queryParameterMap?.let {

            var queryParameterString = "?"

            for ((key, value) in it) {
                queryParameterString += "$key=$value&"
            }

            // drop last '&'
            urlString += queryParameterString.dropLast(1)
        }

        if (client == null) {
            createClient()
        }

        return client!!.request {
            this.method = HttpMethod.Get
            this.url(urlString)
            this.contentType(ContentType.Application.Json)

            // if header
            headerMap?.let {
                for ((key, value) in it) {
                    this.header(key, value)
                }
            }
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun post(
        url: String,
        headerMap: MutableMap<String, String>? = null,
        queryParameterMap: MutableMap<String, String>? = null,
        formDataMap: MutableMap<String, String>? = null,
        formDataFileList: List<FormDataFile>? = null
    ): HttpResponse {

        var urlString = url

        queryParameterMap?.let {

            var queryParameterString = "?"

            for ((key, value) in it) {
                queryParameterString += "$key=$value&"
            }

            // drop last '&'
            urlString += queryParameterString.dropLast(1)
        }

        if (client == null) {
            createClient()
        }

        return client!!.request {
            this.method = HttpMethod.Post
            this.url(url)

            // if header
            headerMap?.let {
                for ((key, value) in it) {
                    this.header(key, value)
                }
            }

            this.body = MultiPartFormDataContent(
                formData {

                    // form data
                    formDataMap?.let {
                        for ((key, value) in it) {
                            this.append(key, value)
                        }
                    }

                    // form data file
                    formDataFileList?.let {

                        for (formDataFile in it) {

                            val file = File(formDataFile.filePath)

                            this.append(formDataFile.fileKey, file.readBytes(), Headers.build {
                                append(HttpHeaders.ContentType, "image/png")
                                append(HttpHeaders.ContentDisposition, "filename=${formDataFile.fileName}")
                            })
                        }
                    }
                }
            )
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun put(
        url: String,
        headerMap: MutableMap<String, String>? = null,
        queryParameterMap: MutableMap<String, String>? = null,
        formDataMap: MutableMap<String, String>? = null,
        formDataFileList: List<FormDataFile>? = null
    ): HttpResponse {

        var urlString = url

        queryParameterMap?.let {

            var queryParameterString = "?"

            for ((key, value) in it) {
                queryParameterString += "$key=$value&"
            }

            // drop last '&'
            urlString += queryParameterString.dropLast(1)
        }

        if (client == null) {
            createClient()
        }

        return client!!.request {
            this.method = HttpMethod.Put
            this.url(url)

            // if header
            headerMap?.let {
                for ((key, value) in it) {
                    this.header(key, value)
                }
            }

            this.body = MultiPartFormDataContent(
                formData {

                    // form data
                    formDataMap?.let {
                        for ((key, value) in it) {
                            this.append(key, value)
                        }
                    }

                    // form data file
                    formDataFileList?.let {

                        for (formDataFile in it) {

                            val file = File(formDataFile.filePath)

                            this.append(formDataFile.fileKey, file.readBytes(), Headers.build {
                                append(HttpHeaders.ContentType, "image/png")
                                append(HttpHeaders.ContentDisposition, "filename=${formDataFile.fileName}")
                            })
                        }
                    }
                }
            )
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun delete(
        url: String,
        headerMap: MutableMap<String, String>? = null,
        queryParameterMap: MutableMap<String, String>? = null,
        formDataMap: MutableMap<String, String>? = null
    ): HttpResponse {

        var urlString = url

        queryParameterMap?.let {

            var queryParameterString = "?"

            for ((key, value) in it) {
                queryParameterString += "$key=$value&"
            }

            // drop last '&'
            urlString += queryParameterString.dropLast(1)
        }

        if (client == null) {
            createClient()
        }

        return client!!.request {
            this.method = HttpMethod.Delete
            this.url(url)

            // if header
            headerMap?.let {
                for ((key, value) in it) {
                    this.header(key, value)
                }
            }

            this.body = MultiPartFormDataContent(
                formData {

                    // form data
                    formDataMap?.let {
                        for ((key, value) in it) {
                            this.append(key, value)
                        }
                    }

                }
            )
        }
    }
}