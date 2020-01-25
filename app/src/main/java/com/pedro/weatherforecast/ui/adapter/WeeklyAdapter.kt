package com.pedro.weatherforecast.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.pedro.weatherforecast.R
import com.pedro.weatherforecast.data.response.response_current.Data
import com.pedro.weatherforecast.internal.epochToDay
import com.pedro.weatherforecast.internal.toPercentage

class WeeklyAdapter(private val list: List<Data?>?) :
    RecyclerView.Adapter<WeeklyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemview =
            LayoutInflater.from(parent.context).inflate(R.layout.item_weekly, parent, false)
        return ViewHolder(itemview)
    }

    override fun getItemCount(): Int = list?.size!!

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list?.get(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val imgIcon = view.findViewById<ImageView>(R.id.imgIcon)
        private val txtTempHigh = view.findViewById<TextView>(R.id.txtHighTemp)
        private val txtTempLow = view.findViewById<TextView>(R.id.txtLowTemp)
        private val txtDate = view.findViewById<TextView>(R.id.txtDate)
        private val txtStatus = view.findViewById<TextView>(R.id.txtStatus)
        private val txtPrecipProb = view.findViewById<TextView>(R.id.txtPrecipProb)

        @SuppressLint("SetTextI18n")
        fun bind(data: Data) {
            txtDate.text = data.time?.epochToDay()
            txtStatus.text = data.summary ?: ""
            txtTempHigh.text = "${data.temperatureHigh?.toInt()}°C"
            txtTempLow.text = "${data.temperatureLow}°C"
            txtPrecipProb.text = data.precipProbability?.toPercentage() ?: ""
            setIcon(data.icon, imgIcon)
        }

        private fun setIcon(icon: String?, imgWeather: ImageView) {
            when (icon) {
                "clear-day" -> imgWeather.load(R.drawable.ic_sun)
                "clear-night" -> imgWeather.load(R.drawable.ic_moon)
                "rain" -> imgWeather.load(R.drawable.ic_cloud_rain)
                "snow" -> imgWeather.load(R.drawable.ic_cloud_snow)
                "sleet" -> imgWeather.load(R.drawable.ic_cloud_double_rain)
                "wind" -> imgWeather.load(R.drawable.ic_cloud_double_rain)
                "fog" -> imgWeather.load(R.drawable.ic_cloud_double)
                "cloudy" -> imgWeather.load(R.drawable.ic_cloud_double)
                "partly-cloudy-day" -> imgWeather.load(R.drawable.ic_cloud_sun)
                "partly-cloudy-night" -> imgWeather.load(R.drawable.ic_cloud_moon)
                else -> imgWeather.load(R.drawable.ic_sun)
            }
        }
    }
}