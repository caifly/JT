package caiflyy.pjy.today.database.weather

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.database.weather
 * 日期：2019/4/12
 * 描述：今日天气数据类
 * @author 蔡葳
 */
data class Now(
    var tmp: String,
    var hum: String,
    var pres: String,
    var cond_code: String,
    var cond_txt: String,
    var wind_dir: String,
    var wind_spd: String,
    var wind_sc: String
)