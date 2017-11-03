package com.example.cavalierrevolt.kotlinweather

import com.beust.klaxon.*
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet

/**
 * Created by cavalierrevolt on 11/2/17.
 */
class API {

    val APIKEY = "8f7f58f0f10d84de71f2c16c1fd86531"
    init {
        FuelManager.instance.basePath = "http://api.openweathermap.org"
    }

    fun queryServer(weather: WeatherLocation): WeatherLocation {
        var details: WeatherDetails

        "/data/2.5/weather?lat=${weather.lat}&lon=${weather.long}&APPID=${APIKEY}".httpGet().responseString { request, response, result ->
            val (data, error) = result
            if (error == null) {
                val parser: Parser = Parser()
                var stringBuilder: StringBuilder = StringBuilder(data)
                val json: JsonObject = parser.parse(stringBuilder) as JsonObject
                details = WeatherDetails(weather)
                details.city = json.string("name")
                json.array<Any>("weather")
                stringBuilder = StringBuilder(json.array<Any>("weather")?.toJsonString())

                val array = parser.parse(stringBuilder) as JsonArray<JsonObject>

                details.mainDescription = array?.string("main")[0]
                details.description = array?.string("description")[0]
                details.humidity = json.obj("main")?.int("humidity")
                details.temp = json.obj("main")?.double("temp")?.toInt()
                details.tempLow = json.obj("main")?.double("temp_min")
                details.tempHigh = json.obj("main")?.double("temp_max")
                details.sunrise = json.obj("sys")?.int("sunrise")
                details.sunset = json.obj("sys")?.int("sunset")

                //do something when success
                 weather.details = details


            } else {
                println(error)
                //error handling
            }
        }

        return weather
    }
}