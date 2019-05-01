package caiflyy.pjy.today.database.weather

import androidx.room.*

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.database.weather
 * 日期：2019/4/14
 * 描述：ForecastHourly表操作接口
 * @author 蔡葳
 */
@Dao
interface ForecastHourlyDao {
    @Query("SELECT * FROM ForecastHourly where weatherId=:wId")
    fun getForecastHourlyById(wId:Long): List<ForecastHourly>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addForecastHourly(hourly: List<ForecastHourly>)

    @Delete
    fun deleteForecastHourly(hourly: ForecastHourly)

    @Update
    fun updateForecastHourly(hourly: List<ForecastHourly>)
}