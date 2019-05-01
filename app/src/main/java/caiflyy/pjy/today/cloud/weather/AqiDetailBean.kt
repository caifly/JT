package caiflyy.pjy.today.data.weather

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.data.weather
 * 日期：2019/4/15
 * 描述：显示空气质量实体类
 * @author 蔡葳
 */
data class AqiDetailBean(
    val name: String,
    val desc: String,
    val value: String="-1"
)