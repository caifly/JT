package caiflyy.pjy.today.database.weather

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.database.weather
 * 日期：2019/4/12
 * 描述：每日天气数据类
 * @author 蔡葳
 */
@Entity(
    foreignKeys = [ForeignKey(
        entity = Weather::class,
        parentColumns = ["id"],
        childColumns = ["weatherId"],
        onDelete = CASCADE
    )], indices = [Index(value = ["weatherId"])]
)
data class ForecastDaily(
    @ColumnInfo(name = "cond_code_d") var cond_code_d: String,
    @ColumnInfo(name = "cond_code_n") var cond_code_n: String,
    @ColumnInfo(name = "cond_txt_d") var cond_txt_d: String,
    @ColumnInfo(name = "cond_txt_n") var cond_txt_n: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "tmp_max") var tmp_max: String,
    @ColumnInfo(name = "tmp_min") var tmp_min: String,
    @ColumnInfo(name = "sr") var sr: String,
    @ColumnInfo(name = "ss") var ss: String,
    @ColumnInfo(name = "mr") var mr: String,
    @ColumnInfo(name = "ms") var ms: String,
    @ColumnInfo(name = "weatherId") var weatherId: Long,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0
)