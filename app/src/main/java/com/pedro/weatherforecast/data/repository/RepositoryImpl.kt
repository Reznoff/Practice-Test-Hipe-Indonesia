package com.pedro.weatherforecast.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pedro.weatherforecast.BuildConfig
import com.pedro.weatherforecast.data.ApiService
import com.pedro.weatherforecast.data.response.response_current.ResponseForecastWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber

class RepositoryImpl(
    private val apiService: ApiService
) : Repository {

    private val _downloadedForecastWeather = MutableLiveData<ResponseForecastWeather>()
    override val downloadedForecastWeather: LiveData<ResponseForecastWeather>
        get() = _downloadedForecastWeather

    override suspend fun getForecastWeather(locationKey: String): LiveData<ResponseForecastWeather> {
        withContext(Dispatchers.IO) {
            try {
                val current = apiService
                    .getForecast(BuildConfig.API_KEY, locationKey, "si")

                if (current.code() == 200) {
                    _downloadedForecastWeather.postValue(current.body())
                }

            } catch (he: HttpException) {
                Timber.e(he)
            }
        }
        return downloadedForecastWeather
    }


//    private val _downloadedForecastWeather = MutableLiveData<ResponseForecastTomorrow>()
//    override val downloadedForecastWeather: LiveData<ResponseForecastTomorrow>
//        get() = _downloadedForecastWeather
//
//    override suspend fun getForecastWeather(locationKey: String): LiveData<ResponseForecastTomorrow> {
//        withContext(Dispatchers.IO) {
//            try {
//                val forecast = apiService.getForecastTomorrowWeather(locationKey, BuildConfig.API_KEY)
//
//                if (forecast.code() == 200)
//                    _downloadedForecastWeather.postValue(forecast.body())
//            } catch (he: HttpException) {
//                Timber.e(he)
//            }
//        }
//        return downloadedForecastWeather
//    }
//
//    private val _downloadedGeoPosition = MutableLiveData<ResponseGeoPosition>()
//    override val downloadedGeoPosition: LiveData<ResponseGeoPosition>
//        get() = _downloadedGeoPosition
//
//    override suspend fun getGeoPosition(latlng: String): LiveData<ResponseGeoPosition> {
//        withContext(Dispatchers.IO) {
//            try {
//                val geoposition = apiService.getGeoPosition(BuildConfig.API_KEY, latlng)
//
//                if (geoposition.code() == 200)
//                    _downloadedGeoPosition.postValue(geoposition.body())
//            } catch (he: HttpException) {
//                Timber.e(he)
//            }
//        }
//        return downloadedGeoPosition
//    }
}