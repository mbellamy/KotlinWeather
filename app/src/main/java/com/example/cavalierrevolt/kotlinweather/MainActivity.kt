package com.example.cavalierrevolt.kotlinweather

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.Manifest.permission
import android.Manifest.permission.INTERNET
import android.Manifest.permission.WRITE_CALENDAR
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.security.Permission


class MainActivity : AppCompatActivity() {

    private val REQUEST_PERMISSION = 1111
    private var locationManager : LocationManager? = null
    var weather : WeatherLocation? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setup location manager
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

        // check for lcoation permission
        checkPermission()
    }




    // check permission function
    fun checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION)
                return
            } else {
                getUserLocation()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_PERMISSION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getUserLocation()
                }
            }
        }

    }


    // get user location
    fun getUserLocation() {
        try {
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0F, locationListener)
        } catch (ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available");
        }
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            weather = WeatherLocation(location.latitude, location.longitude)
            weather?.callback = {
                updateUI()
            }
            if (weather != null) {
                getWeatherAtLocation(weather!!)
            }

        }


        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }


    // get current weather
    fun getWeatherAtLocation(weather: WeatherLocation) {
        weather.getCurrent()
    }


    // update the UI
    fun updateUI() {

        if (weather?.details?.city != null) {
            cityTextView.setText(weather?.details!!.city)
        }
        if (weather?.details?.temp != null) {
            var temp: String = weather?.details!!.temp.toString() + "°"
            tempTextView.setText(temp)
        }
        if (weather?.details?.description != null) {
            descriptionTextView.setText(weather?.details!!.description)
        }


    }

}
