package com.example.alpha_bininfo.data

import retrofit2.http.GET
import retrofit2.http.Path

interface BinApiService {
    @GET("{bin}")
    suspend fun getBinInfo(
        @Path("bin") bin: String
    ): BinInfo
}