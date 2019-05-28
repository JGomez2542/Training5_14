package com.example.mytrainingapp.data.remote

import com.example.mytrainingapp.models.WeatherData
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {

    @GET("data/2.5/forecast")
    fun getWeatherData(@Query("zip") zip: String, @Query("appid") appId: String): Call<ResponseBody>

    @GET("data/2.5/forecast")
    fun getWeatherDataRxJava(@Query("zip") zip: String, @Query("appid") appId: String): Single<WeatherData>
}