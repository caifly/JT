package caiflyy.pjy.today.ui.weather


import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import caiflyy.pjy.today.R
import caiflyy.pjy.today.data.weather.AqiDetailBean
import caiflyy.pjy.today.database.weather.Weather
import caiflyy.pjy.today.repository.TodayRepository
import caiflyy.pjy.today.utils.EXTRA_KEY_CITY_ID
import caiflyy.pjy.today.utils.EXTRA_KEY_CITY_NAME
import caiflyy.pjy.today.utils.TAG
import caiflyy.pjy.today.widgets.dynamic.*
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_city_weather.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern.compile

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.weather
 * 日期：2019/4/14
 * 描述：单个城市天气信息界面
 * @author 蔡葳
 */
class CityWeatherFragment : Fragment(), NestedScrollView.OnScrollChangeListener {
    private val localRect = Rect()
    private var weatherChartViewLastVisible = false
    private var aqiViewLastVisible = false
    private var currentWeather: Weather? = null
    private lateinit var aqiAdapter: AqiAdapter
    private lateinit var suggestionAdapter: SuggestionAdapter
    private val cityWeatherViewModel: CityWeatherViewModel by viewModel()
    private val todayRepository: TodayRepository by inject()
    override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
        if (weatherChartView.getLocalVisibleRect(localRect)) {
            if (!weatherChartViewLastVisible) {
                weatherChartView.setWeather(currentWeather)
            }
            weatherChartViewLastVisible = true
        } else {
            weatherChartViewLastVisible = false
        }

        if (aqiView.getLocalVisibleRect(localRect)) {
            if (!aqiViewLastVisible) {
                aqiView.setApi(currentWeather)
            }
            aqiViewLastVisible = true
        } else {
            aqiViewLastVisible = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cityID = arguments?.getLong(EXTRA_KEY_CITY_ID)!!
        val cityName = arguments?.getString(EXTRA_KEY_CITY_NAME)!!
        swipeContainer.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            todayRepository.getCloudWeatherInfo(cityID,cityName,false)
            swipeContainer.isRefreshing = false
        })
        weatherNestedScrollView.setOnScrollChangeListener(this)
        recyclerView_hourly.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val hourlyAdapter = HourlyAdapter(R.layout.item_weather_hourly, null)
        hourlyAdapter.setDuration(1000)
        hourlyAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        recyclerView_hourly.adapter = hourlyAdapter
        recyclerViewAqi.layoutManager = object : GridLayoutManager(activity, 2) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        aqiAdapter = AqiAdapter(R.layout.item_weather_aqi, null)
        aqiAdapter.setDuration(1000)
        aqiAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        recyclerViewAqi.adapter = aqiAdapter

        recyclerViewSuggestion.layoutManager = object : GridLayoutManager(activity, 4) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        suggestionAdapter = SuggestionAdapter(R.layout.item_weather_suggestion, null)
        suggestionAdapter.setDuration(1000)
        suggestionAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        recyclerViewSuggestion.adapter = suggestionAdapter
        cityWeatherViewModel.onSuccess().observe(this, Observer {
            AnkoLogger(TAG).error("天气信息观察者")
            currentWeather = it
            setDynamicWeatherView(it)
            layoutWeatherNow.visibility = View.VISIBLE
            layout_details.visibility = View.VISIBLE
            tvWeatherNowHum.text = currentWeather?.now?.hum + "%"
            tvWeatherNowPres.text = currentWeather?.now?.pres
            tvWeatherNowWindSc.text =
                if (hasDigit(currentWeather?.now?.wind_sc!!))
                    currentWeather?.now?.wind_sc + "级"
                else
                    currentWeather?.now?.wind_sc

            tvWeatherNowWindDir.text = currentWeather?.now?.wind_dir
            layoutWeatherNow.alpha = 0F
            layout_details.alpha = 0F
            layoutWeatherNow.animate().alpha(1F).duration = 1000
            layout_details.translationY = -100.0F
            layout_details.animate().translationY(0F).duration = 1000
            layout_details.animate().alpha(1F).duration = 1000
            weatherChartView.setWeather(currentWeather)
            tvWeatherNowString.setText(currentWeather?.now?.cond_txt)
            tvWeatherNowTemp.text = String.format("%s°", currentWeather?.now?.tmp)
            tvWeatherTodayTempMax.text = currentWeather?.forecastDaily?.get(0)?.tmp_max + "℃"
            tvWeatherTodayTempMin.text = currentWeather?.forecastDaily?.get(0)?.tmp_min + "℃"
            aqiView.setApi(currentWeather)
            setAqiDetail(currentWeather!!)
            setSuggesstion(currentWeather!!)
            hourlyAdapter.data.clear()
            hourlyAdapter.setNewData(currentWeather?.forecastHourly)
        })
        //获取缓存天气数据
        todayRepository.getCityWeatherInfo(cityID, cityName)
    }

    private fun setDynamicWeatherView(weather: Weather) {
        (parentFragment as WeatherFragment).getDynamicWeatherView().originWeather = weather
        val shortWeatherInfo = ShortWeatherInfo()
        shortWeatherInfo.code = weather.now.cond_code
        shortWeatherInfo.windSpeed = weather.now.wind_spd
        shortWeatherInfo.sunrise = weather.forecastDaily[0].sr
        shortWeatherInfo.sunset = weather.forecastDaily[0].ss
        shortWeatherInfo.moonrise = weather.forecastDaily[0].mr
        shortWeatherInfo.moonset = weather.forecastDaily[0].ms
        val type = getType(shortWeatherInfo)
        (parentFragment as WeatherFragment).getDynamicWeatherView().setType(type)
    }

    private fun getType(shortWeatherInfo: ShortWeatherInfo): BaseWeatherType {
        val code = Integer.parseInt(shortWeatherInfo.code)
        if (code == 100) {//晴
            return SunnyType(context!!, shortWeatherInfo)
        } else if (code >= 101 && code <= 103) {//多云
            val sunnyType = SunnyType(context!!, shortWeatherInfo)
            sunnyType.isCloud = true
            return sunnyType
        } else if (code == 104) {//阴
            return OvercastType(context!!, shortWeatherInfo)
        } else if (code >= 200 && code <= 213) {//各种风
            return SunnyType(context!!, shortWeatherInfo)
        } else if (code >= 300 && code <= 303) {//各种阵雨
            if (code >= 300 && code <= 301) {
                return RainType(context!!, RainType.RAIN_LEVEL_2, RainType.WIND_LEVEL_2)
            } else {
                val rainType = RainType(context!!, RainType.RAIN_LEVEL_2, RainType.WIND_LEVEL_2)
                rainType.setFlashing(true)
                return rainType
            }
        } else if (code == 304) {//阵雨加冰雹
            return HailType(context!!)
        } else if (code >= 305 && code <= 312) {//各种雨
            return if (code == 305 || code == 309) {//小雨
                RainType(context!!, RainType.RAIN_LEVEL_1, RainType.WIND_LEVEL_1)
            } else if (code == 306) {//中雨
                RainType(context!!, RainType.RAIN_LEVEL_2, RainType.WIND_LEVEL_2)
            } else {//大到暴雨
                RainType(context!!, RainType.RAIN_LEVEL_3, RainType.WIND_LEVEL_3)
            }
        } else if (code == 313) {//冻雨
            return HailType(context!!)
        } else if (code >= 400 && code <= 407) {//各种雪
            if (code == 400) {
                return SnowType(context!!, SnowType.SNOW_LEVEL_1)
            } else if (code == 401) {
                return SnowType(context!!, SnowType.SNOW_LEVEL_2)
            } else if (code >= 402 && code <= 403) {
                return SnowType(context!!, SnowType.SNOW_LEVEL_3)
            } else if (code >= 404 && code <= 406) {
                val rainSnowType = RainType(context!!, RainType.RAIN_LEVEL_1, RainType.WIND_LEVEL_1)
                rainSnowType.setSnowing(true)
                return rainSnowType
            } else {
                return SnowType(context!!, SnowType.SNOW_LEVEL_2)
            }
        } else return if (code >= 500 && code <= 501) {//雾
            FogType(context!!)
        } else if (code == 502) {//霾
            HazeType(context!!)
        } else if (code >= 503 && code <= 508) {//各种沙尘暴
            SandstormType(context!!)
        } else if (code == 900) {//热
            SunnyType(context!!, shortWeatherInfo)
        } else if (code == 901) {//冷
            SnowType(context!!, SnowType.SNOW_LEVEL_1)
        } else {//未知
            SunnyType(context!!, shortWeatherInfo)
        }
    }

    /**
     * 判断一个字符串是否含有数字
     * @param content 字符串
     * @return 结果
     */
    private fun hasDigit(content: String): Boolean {
        var flag = false
        val p = compile(".*\\d+.*")
        val m = p.matcher(content)
        if (m.matches()) {
            flag = true
        }
        return flag
    }

    private fun setSuggesstion(weather: Weather) {
        suggestionAdapter.setNewData(weather.lifeStyle)
    }

    private fun setAqiDetail(weather: Weather) {
        val list = ArrayList<AqiDetailBean>()
        val pm25 = AqiDetailBean("PM2.5", "细颗粒物", weather.airQuality.pm25)
        list.add(pm25)
        val pm10 = AqiDetailBean("PM10", "可吸入颗粒物", weather.airQuality.pm10)
        list.add(pm10)
        val so2 = AqiDetailBean("SO2", "二氧化硫", weather.airQuality.so2)
        list.add(so2)
        val no2 = AqiDetailBean("NO2", "二氧化氮", weather.airQuality.no2)
        list.add(no2)
        val co = AqiDetailBean("CO", "一氧化碳", weather.airQuality.co)
        list.add(co)
        val o3 = AqiDetailBean("O3", "臭氧", weather.airQuality.o3)
        list.add(o3)
        aqiAdapter.setNewData(list)
    }
}
