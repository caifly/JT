package caiflyy.pjy.today.ui.weather

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.weather
 * 日期：2019/4/10
 * 描述：天气界面数据提供
 * @author 蔡葳
 */
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import caiflyy.pjy.today.database.city.City
import caiflyy.pjy.today.database.weather.Weather
import caiflyy.pjy.today.repository.TodayRepository

class WeatherViewModel(private val todayRepository:TodayRepository) : ViewModel() {

    fun onSuccess(): LiveData<Weather>{
        return todayRepository.cityWeatherInfo
    }

    fun saveCurrentItem(LauncherActionId: Int) {
//        todayRepository.saveCurrentItem(LauncherActionId)
    }

    fun getCites(): LiveData<List<City>> {
        return todayRepository.getCites()
    }

    fun insertCity() {
        todayRepository.insertCity()
    }
}
