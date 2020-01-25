package com.pedro.weatherforecast.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayout
import com.pedro.weatherforecast.R
import com.pedro.weatherforecast.internal.epochToHour
import com.pedro.weatherforecast.ui.adapter.ViewPagerAdapter
import com.pedro.weatherforecast.ui.viewmodel.MainViewModel
import com.pedro.weatherforecast.ui.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.commons.lang3.StringUtils
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()
    private lateinit var viewModel: MainViewModel
    private val viewModelFactory: MainViewModelFactory by instance()

    private lateinit var dialog: Dialog

    private lateinit var mFusedLocation: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        getLocationPermission()

        setCurrentWeather()
        setTabLayout()
    }

    override fun onResume() {
        super.onResume()
        mFusedLocation = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocation.lastLocation.addOnSuccessListener(this) { location ->
            // Do it all with location
            Timber.i("lat: ${location.latitude}, long: ${location?.longitude}")
            val latlng = "${location.latitude},${location.longitude}"
            location?.let {
                setDialog()
                collapseToolbar.title = getCity(this, location.latitude, location.longitude)
                viewModel.fetchForecastWeather(latlng)
            }
        }
    }


    private fun getLocationPermission() {
        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1001)
            }
            return
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCurrentWeather() {
        if(!viewModel.forecastWeather.hasObservers()) {
            viewModel.forecastWeather.observe(this, Observer { current ->
                current?.let {
                    txtDegree.text = it.currently?.temperature?.toInt().toString()
                    txtUnit.text = "°C"
                    txtStatus.text = it.currently?.summary
                    txtFeelsLike.text = "Feels like ${it.currently?.apparentTemperature?.toInt()}°C"
                    txtUpdated.text = "Updated at ${it.currently?.time?.epochToHour()}"
                    it.currently?.icon?.let { it1 -> setIcon(it1) }
                    dialog.dismiss()
                }
            })
        }
    }

    private fun getCity(context: Context, latitude: Double, longitude: Double): String {
        val gcd = Geocoder(context, Locale.getDefault())
        val addresses = gcd.getFromLocation(latitude, longitude, 1)
        if (addresses.size > 0) {
            val locality = StringUtils.replace(addresses[0].locality, "Kecamatan", "")
            val countryCode = addresses[0].countryCode
            return "$locality, $countryCode"
        }
        return ""
    }

    private fun setIcon(icon: String) {
        when(icon) {
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

    private fun setTabLayout() {
        viewpager.adapter = ViewPagerAdapter(supportFragmentManager)
        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewpager))
    }

    private fun setDialog() {
        dialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.placeholder)
        }
        dialog.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.forecastWeather.removeObservers(this)
    }

}
