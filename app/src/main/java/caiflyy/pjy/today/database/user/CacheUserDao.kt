package caiflyy.pjy.today.database.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update



/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.database.profile
 * 日期：2019/4/14
 * CacheUser表操作接口
 * @author 蔡葳
 */
@Dao
interface CacheUserDao {

    @Query("SELECT * FROM CacheUser")
    fun getCacheUser(): CacheUser?

    @Insert(onConflict = REPLACE)
    fun addCacheUser(user: CacheUser)

    @Update
    fun updateCacheUser(user:CacheUser)

    @Query("DELETE FROM CacheUser")
    fun deleteAll()

}