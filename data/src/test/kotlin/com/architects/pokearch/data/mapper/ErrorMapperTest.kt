package com.architects.pokearch.data.mapper

import com.architects.pokearch.domain.model.error.ErrorType
import org.amshove.kluent.shouldBe
import org.junit.Test

class ErrorMapperTest {

    private val errorMapper = ErrorMapper

    @Test
    fun `returns BadRequest Error For Code 400`() {
        val result = errorMapper.getErrorType(400)

        result shouldBe ErrorType.BadRequest
    }

    @Test
    fun `returns UnAuthorized Error For Code 401`() {
        val result = errorMapper.getErrorType(401)

        result shouldBe ErrorType.Unauthorized
    }

    @Test
    fun `returns NotFound Error For Code 404`() {
        val result = errorMapper.getErrorType(404)

        result shouldBe ErrorType.NotFound
    }

    @Test
    fun `returns TimeOut Error For Code 408`() {
        val result = errorMapper.getErrorType(408)

        result shouldBe ErrorType.TimeOut
    }

    @Test
    fun `returns ServerError For Code 500`() {
        val result = errorMapper.getErrorType(500)

        result shouldBe ErrorType.InternalServerError
    }

    @Test
    fun `returns BadGateway For Code 502`() {
        val result = errorMapper.getErrorType(502)

        result shouldBe ErrorType.BadGateway
    }

    @Test
    fun `returns ServiceUnavailable For Code 503`() {
        val result = errorMapper.getErrorType(503)

        result shouldBe ErrorType.ServiceUnavailable
    }

    @Test
    fun `returns ServiceUnavailable For Code 504`() {
        val result = errorMapper.getErrorType(504)

        result shouldBe ErrorType.GatewayTimeout
    }

    @Test
    fun `returns UnknownError For Unrecognized Code`() {
        val result = errorMapper.getErrorType(999)

        result shouldBe ErrorType.Unknown
    }
}