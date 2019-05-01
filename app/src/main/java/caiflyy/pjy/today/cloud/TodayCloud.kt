package caiflyy.pjy.today.cloud

import caiflyy.pjy.today.cloud.weather.AirQualityService
import caiflyy.pjy.today.cloud.weather.WeatherService
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.cloud
 * 日期：2019/4/14
 * 描述：云端数据操作
 * @author 蔡葳
 */

class TodayCloud {

    private val weatherService: WeatherService
    private val airQualityService: AirQualityService
    private val weatherUrl = "https://free-api.heweather.com/s6/weather/"
    private val airQualityUrl = "https://free-api.heweather.net/s6/air/now/"
    private val params: WeakHashMap<String, Object> = WeakHashMap()

    init {
        params["key"] = "69e47ac0050048619cee219c06ac3b12" as Object
        val retrofit = Retrofit.Builder()
            .baseUrl(weatherUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherService = retrofit.create(WeatherService::class.java)
        airQualityService = retrofit.create(AirQualityService::class.java)
    }

    fun getWeatherInfo(cityName:String,callback: Callback<RepoResult>) {
        params["location"] = cityName as Object
        val call = weatherService.getCityWeather(weatherUrl,params)
        call.enqueue(callback)
    }

    fun getAirInfo(cityName:String,callback: Callback<RepoResult>){
        params["location"] = cityName as Object
        val call = weatherService.getCityWeather(airQualityUrl,params)
        call.enqueue(callback)
    }
}