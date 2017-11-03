package com.example.cavalierrevolt.kotlinweather

import android.os.Handler
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by cavalierrevolt on 11/2/17.
 */

class WeatherLocation(lat: Double, long: Double) {
    val lat = lat
    val long = long
    var callback = {}
    var timestamp: Long = 0

    var details: WeatherDetails? = null
        set(value) {
            field = value
            prepareUI()
        }

    fun getCurrent() {
        if (Date().time - timestamp > 600000) {
            this.timestamp = Date().time
            var weather: WeatherLocation = API().queryServer(this)
            this.details = weather.details
        }
    }


    fun prepareUI() {
        callback()
    }

    override fun toString(): String {
        return "WeatherLocation(lat=$lat, long=$long, callback=$callback, details=$details)"
    }


}
