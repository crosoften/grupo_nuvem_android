package com.crosoften.emnuvem.retrofit

import com.crosoften.emnuvem.ultils.Constants
import com.crosoften.emnuvem.ultils.NuvemApplication
import com.crosoften.emnuvem.ultils.Preference
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitConfig {
    companion object {

        private lateinit var retrofit: Retrofit
        private const val baseUrlProd = Constants.BASE_URL
        private const val CONNECTION_TIMEOUT = 20 * 1000

        private fun getRetrofitInstance(): Retrofit {

            val preferences = Preference(NuvemApplication.instance!!)

            val client =
                OkHttpClient.Builder().addInterceptor { chain ->
                    var newRequest = chain.request().newBuilder()
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
                }.connectTimeout(
                    CONNECTION_TIMEOUT.toLong(),
                    TimeUnit.MINUTES
                ).readTimeout(1, TimeUnit.MINUTES).build()

            if (!::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrlProd)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit
        }

        fun <S> createService(serviceClass: Class<S>): S {
            return getRetrofitInstance().create(serviceClass)
        }

    }

}