package com.pedro.weatherforecast.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedro.weatherforecast.data.repository.Repository
import com.pedro.weatherforecast.data.response.response_current.ResponseForecastWeather
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _forecastWeather = MutableLiveData<ResponseForecastWeather>()
    val forecastWeather: LiveData<ResponseForecastWeather>
        get() = _forecastWeather

    fun fetchForecastWeather(locationKey: String) {
        viewModelScope.launch {
            val current = repository.getForecastWeather(locationKey)
            _forecastWeather.postValue(current.value)
        }
    }


    override fun onCleared() {
        super.onCleared()

    }

}