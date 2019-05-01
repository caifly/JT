package caiflyy.pjy.today.database.city

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.database.city
 * 日期：2019/4/14
 * City表操作接口
 * @author 蔡葳
 */
@Dao
interface CityDao {

    @Query("SELECT * FROM city")
    fun getAllCity(): LiveData<List<City>>

    @Insert(onConflict = REPLACE)
    fun addCity(city: City)

}