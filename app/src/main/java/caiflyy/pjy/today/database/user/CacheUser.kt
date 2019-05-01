package caiflyy.pjy.today.database.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.database.profile
 * 日期：2019/4/12
 * 描述：登录用户信息数据类
 * @author 蔡葳
 */
@Entity
data class CacheUser(
    @ColumnInfo(name = "objectId") val objectId: String,
    @ColumnInfo(name = "cityId") val cityId: String,
    @ColumnInfo(name = "sex") var sex: Boolean,
    @ColumnInfo(name = "type") val type: Int,
    @ColumnInfo(name = "nickName") var nickName: String,
    @ColumnInfo(name = "headIcon") var headIcon: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "email") var email: String="没有设置",
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long = 0
)