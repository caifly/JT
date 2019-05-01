package caiflyy.pjy.today.database.weather

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.database.weather
 * 日期：2019/4/14
 * 描述：Weather表操作接口
 * @author 蔡葳
 */
@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather where cityId=:cId")
    fun getWeatherById(cId:Long): Weather?

    @Insert(onConflict = REPLACE)
    fun addWeather(weather: Weather)

    @Delete
    fun deleteWeather(weather: Weather)

    @Update
    fun updateWeather(weather: Weather)
}