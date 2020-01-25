package com.pedro.weatherforecast.ui.weekly


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedro.weatherforecast.R
import com.pedro.weatherforecast.ui.adapter.WeeklyAdapter
import com.pedro.weatherforecast.ui.viewmodel.MainViewModel
import com.pedro.weatherforecast.ui.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_weekly.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class WeeklyFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()
    private lateinit var viewModel: MainViewModel
    private val viewModelFactory: MainViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(MainViewModel::class.java)
        return inflater.inflate(R.layout.fragment_weekly, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.forecastWeather.observe(viewLifecycleOwner, Observer {
            it?.let {forecast ->
                val adapter = WeeklyAdapter(forecast.daily?.data)
                listForecastTomorrow.adapter = adapter
                listForecastTomorrow.layoutManager = LinearLayoutManager(requireContext())
                listForecastTomorrow.setHasFixedSize(true)
                adapter.notifyDataSetChanged()
            }
        })
    }

}
