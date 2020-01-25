package com.pedro.weatherforecast

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.pedro.weatherforecast.data.ApiService
import com.pedro.weatherforecast.data.repository.Repository
import com.pedro.weatherforecast.data.repository.RepositoryImpl
import com.pedro.weatherforecast.ui.viewmodel.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import timber.log.Timber

class WeatherApplication: Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@WeatherApplication))

        //Provide some context dependency
        bind() from singleton { ApiService(instance()) }
        bind<Repository>() with singleton { RepositoryImpl(instance()) }
        bind() from singleton {
            MainViewModelFactory(
                instance()
            )
        }
//        bind() from singleton { DetailJadwalViewModelFactory(instance()) }
//        bind() from singleton { CariViewModelFactory(instance()) }
//        bind() from singleton { HasilCariViewModelFactory(instance()) }
//        bind() from singleton { LelangAgunanViewModelFactory(instance()) }
//        bind() from singleton { JadwalAgunanViewModelFactory(instance()) }
//        bind() from singleton { SplashViewModelFactory(instance()) }
//        bind() from singleton { LainnyaViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        timberInit()
        hawkInit()
    }

    private fun timberInit() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    private fun hawkInit() {
        Hawk.init(this)
            .build()
    }

}