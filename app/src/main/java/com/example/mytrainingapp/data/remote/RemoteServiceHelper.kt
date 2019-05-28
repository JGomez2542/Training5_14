package com.example.mytrainingapp.data.remote

import android.content.Context
import com.example.mytrainingapp.common.APPID
import com.example.mytrainingapp.common.WEATHER_BASE_URL
import com.example.mytrainingapp.common.ZIP
import com.example.mytrainingapp.models.WeatherData
import io.reactivex.Single
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RemoteServiceHelper(context: Context) {

    private val okHttpClient: OkHttpClient
    private val retrofit: Retrofit

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)
        okHttpClient = OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor(interceptor)
            .addInterceptor { chain ->

                // Get the request from the chain.
                val request = chain.request()
                request.newBuilder().header("Cache-Control", "public, max-age=5").build()

                //Add the modified request to the chain
                chain.proceed(request)
            }
            .build()

        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(WEATHER_BASE_URL)
            .build()
    }

    fun getWeatherData(): Call<ResponseBody> {
        val service = retrofit.create(RemoteService::class.java)
        return service.getWeatherData(ZIP, APPID)
    }

    fun getWeatherDataRxJava(): Single<WeatherData> {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val service = retrofit.create(RemoteService::class.java)
        return service.getWeatherDataRxJava(ZIP, APPID)
    }
}