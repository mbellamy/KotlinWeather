package com.example.cavalierrevolt.kotlinweather

import java.lang.Math.round

/**
 * Created by cavalierrevolt on 11/2/17.
 */
class WeatherDetails(weatherLocation: WeatherLocation?) {

    var lat: Double? = weatherLocation?.lat ?: 0.00
    var long: Double? = weatherLocation?.long ?: 0.00
    var mainDescription: String? = null
    var description: String? = null
    var temp: Int? = null
        set(value) {
            field = kevlvinToF(value)
        }
    var humidity: Int? = null
    var tempLow: Double? = null
    var tempHigh: Double? = null
    var city: String? = null
    var sunrise: Int? = null
    var sunset: Int? = null

    var fahr: Boolean = false

    fun kevlvinToF(temp: Int?): Int? {
        this.fahr = false

        if (temp != null) {
            this.fahr = true
            return  round(temp!! * 9/5 - 459.67).toInt()
        }
        return temp
    }
    override fun toString(): String {
        return "WeatherDetails(lat=$lat, long=$long, mainDescription=$mainDescription, description=$description, temp=$temp, humidity=$humidity, tempLow=$tempLow, tempHigh=$tempHigh, city=$city, sunrise=$sunrise, sunset=$sunset)"
    }


}