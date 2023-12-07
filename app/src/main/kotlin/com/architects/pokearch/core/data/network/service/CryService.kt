package com.architects.pokearch.core.data.network.service

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CryService {
    @GET("{name}.mp3")
    suspend fun thereIsCry(@Path("name") name: String): Response<ResponseBody>
}
