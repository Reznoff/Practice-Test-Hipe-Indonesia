package com.pedro.weatherforecast.data.response.response_current


import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("data")
    val data: List<Data?>?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("summary")
    val summary: String?
)