package caiflyy.pjy.today.database.weather

import androidx.room.*


/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.database.weather
 * 日期：2019/4/12
 * 描述：天气数据类
 * @author 蔡葳
 */
@Entity
data class Weather(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "cityId") var cityId: Long=0
    ){
    @Embedded lateinit var now: Now
    @Ignore lateinit var forecastHourly: List<ForecastHourly>
    @Ignore lateinit var forecastDaily: List<ForecastDaily>
    @Embedded lateinit var airQuality: AirQuality
    @Ignore lateinit var lifeStyle: List<LifeStyle>

}
