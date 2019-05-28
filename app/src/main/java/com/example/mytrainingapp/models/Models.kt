package com.example.mytrainingapp.models

import com.google.gson.annotations.SerializedName

data class City(val id: Int, val name: String, val coord: Coord, val country: String, val population: Int)
data class Clouds(val all: Int)
data class Coord(val lat: Double, val lon: Double)
data class Rain(@SerializedName("3h") val _3h: Double)
data class Sys(val pod: String)
data class Weather(val id: Int, val main: String, val description: String, val icon: String)
data class WeatherData(val cod: String, val message: Double, val cnt: Int, val list: List<WeatherList>, val city: City)
data class Wind(val speed: Double, val deg: Double)
data class RestCallPerson(val name: String, val age: Int, val weight: Int, val nationality: String)
data class RxJavaPerson(val name: String, val friends: List<String>)

data class WeatherList(
    val dt: Int, val main: Main, val weather: List<Weather>,
    val clouds: Clouds, val wind: Wind, val rain: Rain,
    val sys: Sys, @SerializedName("dt_txt") val dtTxt: String
)

data class Main(
    val temp: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    val pressure: Double,
    @SerializedName("sea_level")
    val seaLevel: Double,
    @SerializedName("grnd_level")
    val grndLevel: Double,
    val humidity: Int,
    @SerializedName("temp_kf")
    val tempKf: Double
)