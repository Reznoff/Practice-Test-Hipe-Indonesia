package com.pedro.weatherforecast.data.response.response_current


import com.google.gson.annotations.SerializedName

data class Flags(
    @SerializedName("sources")
    val sources: List<String?>?,
    @SerializedName("units")
    val units: String?
)