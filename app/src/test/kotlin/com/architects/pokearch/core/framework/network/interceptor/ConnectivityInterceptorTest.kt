package com.architects.pokearch.core.framework.network.interceptor

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class ConnectivityInterceptorTest {

    private val connectivityManager: ConnectivityManager = mockk(relaxed = true)
    private val connectivityInterceptor: ConnectivityInterceptor = ConnectivityInterceptor(connectivityManager)
    private val chain: Interceptor.Chain = mockk(relaxed = true)
    private lateinit var request: Request
    private lateinit var response: Response

    @Before
    fun setUp() {
        request = Request.Builder().url("http://example.com").build()
        response = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .message("OK")
            .code(200)
            .body("OK".toResponseBody("text/plain".toMediaType()))
            .build()

        every { chain.request() } returns request
        every { chain.proceed(request) } returns response
    }

    @Test
    fun `GIVEN intercept WHEN connected THEN returns response`() {
        every { connectivityManager.activeNetwork } returns mockk()
        every { connectivityManager.getNetworkCapabilities(any()) } returns mockk {
            every { hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) } returns true
        }

        val result = connectivityInterceptor.intercept(chain)

        result shouldBeEqualTo response

        verify { chain.proceed(request) }
    }

    @Test(expected = InternetConnectivityException::class)
    fun `GIVEN intercept WHEN not connected THEN throws InternetConnectivityException`() {
        every { connectivityManager.activeNetwork } returns null

        connectivityInterceptor.intercept(chain)
    }
}