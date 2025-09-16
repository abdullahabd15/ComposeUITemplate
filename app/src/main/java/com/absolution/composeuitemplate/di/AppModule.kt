package com.absolution.composeuitemplate.di

import com.absolution.composeuitemplate.ui.viewmodel.ChatListViewModel
import com.absolution.composeuitemplate.ui.viewmodel.ChatViewModel
import com.absolution.composeuitemplate.ui.viewmodel.LoginViewModel
import com.absolution.composeuitemplate.ui.viewmodel.SignUpViewModel
import com.absolution.composeuitemplate.ui.viewmodel.SubscriptionPlansViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
//    single {
//        SubscriptionPlansViewModel()
//        LoginViewModel()
//        SignUpViewModel()
//        ChatListViewModel()
//        ChatViewModel()
//    }
//    factory {
//        SubscriptionPlansViewModel()
//        LoginViewModel()
//        SignUpViewModel()
//        ChatListViewModel()
//        ChatViewModel()
//    }
//    viewModel {
//        SubscriptionPlansViewModel()
//        LoginViewModel()
//        SignUpViewModel()
//        ChatListViewModel()
//        ChatViewModel()
//    }
    viewModelOf(::SubscriptionPlansViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::ChatListViewModel)
    viewModelOf(::ChatViewModel)
}