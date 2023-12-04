package com.example.compose.rally.ui.chat

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openai.com/")
        .client(createOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val openAIApi: OpenAIApi by lazy {
        retrofit.create(OpenAIApi::class.java)
    }
    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(ApiUtil().getLastApi()))
            .build()
    }
}

class AuthInterceptor(private val apiUtil: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer sk- $apiUtil")
            .build()
        return chain.proceed(newRequest)
    }
}

class ApiUtil {
    var apiList: List<String> = listOf()

    fun getLastApi(): String {
        if (apiList.isNotEmpty()) {
            return apiList.last()
        }
        throw IllegalAccessException("ApiList is empty")
    }
}