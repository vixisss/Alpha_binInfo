package com.example.alpha_bininfo.di

import com.example.alpha_bininfo.presentation.BinViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        BinViewModel(get())
    }
}