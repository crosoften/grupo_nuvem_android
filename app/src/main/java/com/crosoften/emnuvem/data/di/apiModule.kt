package com.crosoften.emnuvem.data.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.crosoften.emnuvem.data.service.Service
import com.crosoften.emnuvem.ultils.Constants
import com.crosoften.emnuvem.ultils.Preference
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module {
    single<OkHttpClient> {
        val preferences = Preference(get())

        OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .header("ContentFaqResponse-Type", "application/json")
                .header(
                    "cookie",
                    "EBSPortalProd=${
                        preferences.getToken()
                    }"
                )
                .header(
                    "Authorization",
                    "Bearer ${
                        preferences.getToken()
                    }"
                )
                .build()
            chain.proceed(newRequest)
        }.addInterceptor(ChuckerInterceptor(context = get())).connectTimeout(
            1,
            TimeUnit.MINUTES
        ).readTimeout(1, TimeUnit.MINUTES).build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<Service>{get<Retrofit>().create(Service::class.java)}
}