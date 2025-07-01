package com.example.alpha_bininfo.di


import com.example.alpha_bininfo.domain.GetBinInfoUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetBinInfoUseCase(get()) }
}