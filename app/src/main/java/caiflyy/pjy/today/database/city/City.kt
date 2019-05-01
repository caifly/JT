package caiflyy.pjy.today.database.city

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.database.city
 * 日期：2019/4/12
 * 描述：城市信息数据类
 * @author 蔡葳
 */
@Entity
data class City(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "name") var name: String=""
)