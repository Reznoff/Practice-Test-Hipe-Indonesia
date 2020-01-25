package com.pedro.weatherforecast.ui.today


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedro.weatherforecast.R
import com.pedro.weatherforecast.data.response.response_current.ResponseForecastWeather
import com.pedro.weatherforecast.internal.toPercentage
import com.pedro.weatherforecast.ui.adapter.TodayAdapter
import com.pedro.weatherforecast.ui.viewmodel.MainViewModel
import com.pedro.weatherforecast.ui.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_today.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class TodayFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()
    private lateinit var viewModel: MainViewModel
    private val viewModelFactory: MainViewModelFactory by instance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(MainViewModel::class.java)
        return inflater.inflate(R.layout.fragment_today, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.forecastWeather.observe(viewLifecycleOwner, Observer {
            it?.let {forecast ->
                setDetail(forecast)
                val adapter = TodayAdapter(forecast.hourly?.data)
                listForecastToday.adapter = adapter
                listForecastToday.layoutManager = LinearLayoutManager(requireContext())
                listForecastToday.setHasFixedSize(true)
                adapter.notifyDataSetChanged()
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setDetail(forecast: ResponseForecastWeather) {
        txtHumidity.text = forecast.currently?.humidity?.toPercentage()
        txtDewPoint.text = "${forecast.currently?.dewPoint}°C"
        txtPressure.text = "${forecast.currently?.pressure} mBar"
        txtUvIndex.text = forecast.currently?.uvIndex.toString()
        txtVisibility.text = "${forecast.currently?.visibility} km"
    }


}
