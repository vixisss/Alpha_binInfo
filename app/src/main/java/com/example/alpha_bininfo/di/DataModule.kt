package com.example.alpha_bininfo.di

import com.example.alpha_bininfo.data.BinApiService
import com.example.alpha_bininfo.data.BinRepositoryImpl
import com.example.alpha_bininfo.data.HistoryStorage
import com.example.alpha_bininfo.domain.BinRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single { HistoryStorage(get()) }

    single {
        Retrofit.Builder()
            .baseUrl("https://lookup.binlist.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BinApiService::class.java)
    }

    single<BinRepository> { BinRepositoryImpl(get(), get()) }
}