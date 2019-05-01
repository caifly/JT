package caiflyy.pjy.today.database

import androidx.room.Database
import androidx.room.RoomDatabase
import caiflyy.pjy.today.database.city.City
import caiflyy.pjy.today.database.city.CityDao
import caiflyy.pjy.today.database.user.CacheUser
import caiflyy.pjy.today.database.user.CacheUserDao
import caiflyy.pjy.today.database.weather.*

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.data.database
 * 日期：2019/4/14
 * 描述：数据库生成类
 * @author 蔡葳
 */
@Database(
    entities = [
        (City::class),
        (Weather::class),
        (ForecastHourly::class),
        (ForecastDaily::class),
        (LifeStyle::class),
        (CacheUser::class)
    ],version = 1,exportSchema = false
)
abstract class TodayDatabase: RoomDatabase() {
    abstract fun getCityDao(): CityDao
    abstract fun getWeatherDao(): WeatherDao
    abstract fun getForecastHourlyDao(): ForecastHourlyDao
    abstract fun getForecastDailyDao(): ForecastDailyDao
    abstract fun getLifeStyleDao(): LifeStyleDao
    abstract fun getCacheUserDao(): CacheUserDao

}