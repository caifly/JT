package caiflyy.pjy.today.ui.weather


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import caiflyy.pjy.today.database.weather.Weather
import caiflyy.pjy.today.repository.TodayRepository
/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.weather
 * 日期：2019/4/10
 * 描述：为城市天气界面数据提供
 * @author 蔡葳
 */
class CityWeatherViewModel(private val todayRepository:TodayRepository) : ViewModel() {

    fun onSuccess(): LiveData<Weather>{
        return todayRepository.cityWeatherInfo
    }
}
