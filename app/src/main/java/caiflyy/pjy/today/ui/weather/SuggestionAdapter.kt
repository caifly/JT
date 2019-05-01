package caiflyy.pjy.today.ui.weather

import caiflyy.pjy.today.R
import caiflyy.pjy.today.database.weather.LifeStyle
import caiflyy.pjy.today.widgets.CircleImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.koin.core.KoinComponent

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.weather
 * 日期：2019/4/15
 * 描述：建议信息适配器
 * @author 蔡葳
 */
class SuggestionAdapter(layoutResId: Int, data: MutableList<LifeStyle>?) :
    BaseQuickAdapter<LifeStyle, BaseViewHolder>(layoutResId, data), KoinComponent {

    val AIR = "空气"
    val COMF = "舒适度"
    val CW = "洗车"
    val DRSG = "穿衣"
    val FLU = "感冒"
    val SPORT = "运动"
    val TRAV = "旅游"
    val UV = "紫外线"

    override fun convert(holder: BaseViewHolder?, item: LifeStyle) {
        updateData(item)
        val circleImageView = holder?.getView<CircleImageView>(R.id.civ_suggesstion)
        holder?.setText(R.id.tvName, item.type)
        holder?.setText(R.id.tvMsg, item.brf)
        circleImageView?.setFillColor(item.iconBackgroudColor)
        circleImageView?.setImageResource(item.icon)
    }

    private fun updateData(item: LifeStyle) {
        when (item.type) {
            "air" -> {
                item.icon = R.drawable.vector_weather_ic_air
                item.iconBackgroudColor = -0x806117
                item.type = AIR
            }
            "comf" -> {
                item.icon = R.drawable.vector_weather_ic_comf
                item.iconBackgroudColor = -0x1661c4
                item.type = COMF
            }
            "cw" -> {
                item.icon = R.drawable.vector_weather_ic_cw
                item.iconBackgroudColor = -0x9d4e01
                item.type = CW
            }
            "drsg" -> {
                item.icon = R.drawable.vector_weather_ic_drsg
                item.iconBackgroudColor = -0x703aa1
                item.type = DRSG
            }
            "flu" -> {
                item.icon = R.drawable.vector_weather_ic_flu
                item.iconBackgroudColor = -0x67e88
                item.type = FLU
            }
            "sport" -> {
                item.icon = R.drawable.vector_weather_ic_sport
                item.iconBackgroudColor = -0x4c35a0
                item.type = SPORT
            }
            "trav" -> {
                item.icon = R.drawable.vector_weather_ic_trav
                item.iconBackgroudColor = -0x293cb
                item.type = TRAV
            }
            "uv" -> {
                item.icon = R.drawable.vector_weather_ic_uv
                item.iconBackgroudColor = -0xf54d6
                item.type = UV
            }
            else -> {
                item.icon = R.drawable.vector_weather_ic_air
                item.iconBackgroudColor = -0x806117
                item.type = CW
            }
        }
    }
}