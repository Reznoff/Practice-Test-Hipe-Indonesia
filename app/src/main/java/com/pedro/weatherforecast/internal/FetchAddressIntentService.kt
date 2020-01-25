package com.pedro.weatherforecast.internal

import android.app.IntentService
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.ResultReceiver
import timber.log.Timber
import java.io.IOException
import java.util.*

class FetchAddressIntentService : IntentService("Location Service") {

    private var receiver: ResultReceiver? = null

    override fun onHandleIntent(intent: Intent?) {
        intent ?: return

        val geocoder = Geocoder(this, Locale.getDefault())
        var errorMessage = ""
        val location: Location? = intent.getParcelableExtra(Constants.LOCATION_DATA_EXTRA)
        var addresses: List<Address> = emptyList()

        try {
            location?.let {
                addresses = geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1)
            }
        } catch (ioe: IOException) {
            errorMessage = "Service not available"
            Timber.e(ioe)
        } catch (iae: IllegalArgumentException) {
            errorMessage = "Invalid LatLng Used"
            Timber.e(iae)
        }

        if(addresses.isNullOrEmpty()) {
                if(errorMessage.isEmpty()) {
                errorMessage = "No Address Found"
                Timber.e(errorMessage)
            }
            deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage)
        } else {
            val address = addresses[0]
            val addressFragments = with(address) {
                (0..maxAddressLineIndex).map {
//                    getAddressLine(it)
                    locality
                }
            }
            Timber.i("Address Found")
            deliverResultToReceiver(Constants.SUCCESS_RESULT, addressFragments.joinToString(separator = "\n"))
        }

    }

    private fun deliverResultToReceiver(resultCode: Int, message: String) {
        val bundle = Bundle().apply { putString(Constants.RESULT_DATA_KEY, message) }
        receiver?.send(resultCode, bundle)
    }

    object Constants {
        const val SUCCESS_RESULT = 0
        const val FAILURE_RESULT = 1
        const val PACKAGE_NAME = "com.pedro.weatherforecast"
        const val RECEIVER = "$PACKAGE_NAME.RECEIVER"
        const val RESULT_DATA_KEY = "${PACKAGE_NAME}.RESULT_DATA_KEY"
        const val LOCATION_DATA_EXTRA = "${PACKAGE_NAME}.LOCATION_DATA_EXTRA"
    }
}
