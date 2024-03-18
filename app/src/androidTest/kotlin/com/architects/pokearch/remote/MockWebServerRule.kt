package com.architects.pokearch.remote

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockWebServerRule : TestWatcher() {

    private lateinit var server: MockWebServer
    override fun starting(description: Description?) {
        server = MockWebServer().apply {
            start(8080)
            dispatcher = MockDispatcher()
        }
    }

    override fun finished(description: Description?) {
        server.shutdown()
    }
}

private class MockDispatcher : Dispatcher() {

    private val validEndpoints = mapOf(
        "/pokemon/" to MockResponse().fromJson("fetch_pokemon.json"),
        "/pokemon?limit=1&offset=0" to MockResponse().fromJson("are_more_available.json"),
        "/pokemon?limit" to MockResponse().fromJson("fetch_pokemons.json")
    )

    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.requestUrl.toString()

        val response = validEndpoints.entries.find { entry ->
            path.contains(entry.key)
        }?.value

        return response ?: MockResponse().setResponseCode(404)
    }
}