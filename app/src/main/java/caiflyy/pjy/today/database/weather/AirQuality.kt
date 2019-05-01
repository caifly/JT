package caiflyy.pjy.today.database.weather

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.database.weather
 * 日期：2019/4/12
 * 描述：空气质量数据类
 * @author 蔡葳
 */
data class AirQuality(
    var aqi: String,
    var qlty: String,
    var main: String,
    var pm25: String,
    var pm10: String,
    var no2: String,
    var so2: String,
    var co: String,
    var o3: String,
    var pub_time: String
)