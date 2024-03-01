package com.architects.pokearch.di

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockWebServerRule: TestWatcher() {

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

private class MockDispatcher: Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.requestUrl?.query
        return MockResponse()
    }
}