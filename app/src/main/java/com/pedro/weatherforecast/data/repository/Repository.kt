package com.pedro.weatherforecast.data.repository

import androidx.lifecycle.LiveData
import com.pedro.weatherforecast.data.response.response_current.ResponseForecastWeather

interface Repository {

    // LiveData
    val downloadedForecastWeather: LiveData<ResponseForecastWeather>
//    val downloadedForecastWeather: LiveData<ResponseForecastTomorrow>
//    val downloadedGeoPosition: LiveData<ResponseGeoPosition>


    // Method
    suspend fun getForecastWeather(locationKey: String): LiveData<ResponseForecastWeather>
//    suspend fun getForecastWeather(locationKey: String): LiveData<ResponseForecastTomorrow>
//    suspend fun getGeoPosition(latlng: String): LiveData<ResponseGeoPosition>
}