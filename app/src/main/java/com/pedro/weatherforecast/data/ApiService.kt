package com.pedro.weatherforecast.data

import android.content.Context
import com.google.gson.GsonBuilder
import com.pedro.weatherforecast.BuildConfig
import com.pedro.weatherforecast.data.response.response_current.ResponseForecastWeather
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {

//    @GET("/locations/v1/cities/geoposition/search")
    @GET("/forecast/{apikey}/{q}")
    suspend fun getForecast(
    @Path("apikey") accessKey: String,
    @Path("q") latlng: String,
    @Query("units") unit: String
    ): Response<ResponseForecastWeather>

//    @GET("/currentconditions/v1/{locationKey}")
//    suspend fun getCurrentWeather(
//        @Path("locationKey") locationKey: String,
//        @Query("apikey") accessKey: String
//    ): Response<Any>
//
//    @GET("/forecasts/v1/daily/1day/{locationKey}")
//    suspend fun getForecastTomorrowWeather(
//        @Path("locationKey") locationKey: String,
//        @Query("apikey") accessKey: String
//    ): Response<ResponseForecastTomorrow>


    companion object {
        operator fun invoke(context: Context): ApiService {
            val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").serializeNulls().create()
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(ChuckInterceptor(context))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30L, TimeUnit.SECONDS)
                .writeTimeout(30L, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }
    }
}