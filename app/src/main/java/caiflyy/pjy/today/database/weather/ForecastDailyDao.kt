package caiflyy.pjy.today.database.weather

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.database.weather
 * 日期：2019/4/14
 * 描述：ForecastDaily表操作接口
 * @author 蔡葳
 */
@Dao
interface ForecastDailyDao {
    @Query("SELECT * FROM ForecastDaily where weatherId=:wId")
    fun getForecastDailyById(wId:Long): List<ForecastDaily>

    @Insert(onConflict = REPLACE)
    fun addForecastDaily(daily: List<ForecastDaily>)

    @Delete
    fun deleteForecastDaily(daily: ForecastDaily)

    @Update
    fun updateForecastDaily(daily: List<ForecastDaily>)
}