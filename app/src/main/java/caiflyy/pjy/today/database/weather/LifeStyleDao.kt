package caiflyy.pjy.today.database.weather

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.database.weather
 * 日期：2019/4/14
 * 描述：LifeStyle表操作接口
 * @author 蔡葳
 */
@Dao
interface LifeStyleDao {
    @Query("SELECT * FROM LifeStyle where weatherId=:wId")
    fun getLifeStyleById(wId:Long): List<LifeStyle>

    @Insert(onConflict = REPLACE)
    fun addLifeStyle(lifeStyle: List<LifeStyle>)

    @Delete
    fun deleteLifeStyle(lifeStyle: LifeStyle)

    @Update
    fun updateLifeStyle(lifeStyle: List<LifeStyle>)
}