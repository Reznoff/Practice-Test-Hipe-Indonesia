package com.pedro.weatherforecast.data.response.response_current


import com.google.gson.annotations.SerializedName

data class ResponseForecastWeather(
    @SerializedName("currently")
    val currently: Currently?,
    @SerializedName("daily")
    val daily: Daily?,
    @SerializedName("flags")
    val flags: Flags?,
    @SerializedName("hourly")
    val hourly: Hourly?,
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?,
    @SerializedName("offset")
    val offset: Int?,
    @SerializedName("timezone")
    val timezone: String?
)