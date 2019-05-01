package caiflyy.pjy.today.cloud

import caiflyy.pjy.today.database.weather.*

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.cloud
 * 日期：2019/4/16
 * 描述：云端天气请求返回类
 * @author 蔡葳
 */
data class RepoResult(val HeWeather6: List<Item>)

data class Item(
    val daily_forecast: List<ForecastDaily>?,
    val hourly: List<ForecastHourly>?,
    val now: Now?,
    val lifestyle: List<LifeStyle>?,
    val air_now_city: AirQuality?
)

