package caiflyy.pjy.today.database.weather

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.database.weather
 * 日期：2019/4/12
 * 描述：每小时天气数据类
 * @author 蔡葳
 */
@Entity(foreignKeys = [ForeignKey(
    entity = Weather::class,
    parentColumns = ["id"],
    childColumns = ["weatherId"],
    onDelete = CASCADE
)],indices = [Index(value = ["weatherId"])])
data class ForecastHourly(
    @ColumnInfo(name = "tmp") var tmp: String,
    @ColumnInfo(name = "time") var time: String,
    @ColumnInfo(name = "cond_code") var cond_code: String,
    @ColumnInfo(name = "weatherId") var weatherId: Long,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0
)