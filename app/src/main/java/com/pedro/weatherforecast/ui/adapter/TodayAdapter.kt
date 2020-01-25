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
import com.pedro.weatherforecast.data.response.response_current.DataX
import com.pedro.weatherforecast.internal.epochToHour
import com.pedro.weatherforecast.internal.toPercentage

class TodayAdapter(private val list: List<DataX?>?) :
    RecyclerView.Adapter<TodayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemview =
            LayoutInflater.from(parent.context).inflate(R.layout.item_hourly, parent, false)
        return ViewHolder(itemview)
    }

    override fun getItemCount(): Int = list?.size!!

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list?.get(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        //        val cardHour = view.findViewById<MaterialCardView>(R.id.cardHour)
        private val imgIcon = view.findViewById<ImageView>(R.id.imgIcon)
        private val imgPrecip = view.findViewById<ImageView>(R.id.imgPrecipitation)
        private val txtTemp = view.findViewById<TextView>(R.id.txtTemp)
        private val txtTempFeels = view.findViewById<TextView>(R.id.txtTempFeels)
        private val txtDate = view.findViewById<TextView>(R.id.txtDate)
        private val txtStatus = view.findViewById<TextView>(R.id.txtStatus)
        private val txtPrecipProb = view.findViewById<TextView>(R.id.txtPrecipitationProb)

        @SuppressLint("SetTextI18n")
        fun bind(data: DataX) {
            txtDate.text = data.time?.epochToHour() ?: ""
            txtStatus.text = data.summary ?: ""
            txtTemp.text = "${data.temperature?.toInt()}°C"
            txtTempFeels.text = "Feels like ${data.apparentTemperature}°C"
            txtPrecipProb.text = data.precipProbability?.toPercentage() ?: ""
            setIcon(data.icon, imgIcon)

            if (data.precipProbability!! >= 0.50) {
                imgPrecip.load(R.drawable.ic_cloud_double_rain)
            } else {
                imgPrecip.load(R.drawable.ic_cloud_rain)
            }
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