package caiflyy.pjy.today.database.weather

import androidx.room.*

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.database.weather
 * 日期：2019/4/12
 * 描述：生活建议数据类
 * @author 蔡葳
 */
@Entity(
    foreignKeys = [ForeignKey(
        entity = Weather::class,
        parentColumns = ["id"],
        childColumns = ["weatherId"],
        onDelete = ForeignKey.CASCADE
    )], indices = [Index(value = ["weatherId"])]
)
data class LifeStyle(
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "brf") var brf: String,
    @ColumnInfo(name = "txt") var txt: String,
    @ColumnInfo(name = "icon") var icon: Int,
    @ColumnInfo(name = "iconBackgroudColor") var iconBackgroudColor: Int,
    @ColumnInfo(name = "weatherId") var weatherId: Long,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0
)