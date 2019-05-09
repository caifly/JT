package caiflyy.pjy.today.ui.demo

import android.graphics.Color
import java.util.*

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.demo
 * 日期：2019-05-06
 * 描述：
 * @author 蔡葳
 */
class ColorWheel {
    private val colors = arrayOf(
        "#39add1",
        "#3079ab",
        "#c25975",
        "#e15258",
        "#f9845b",
        "#838cc7",
        "#7d669e",
        "#53bbb4",
        "#51b46d",
        "#e0ab18",
        "#637a91",
        "#f092b0",
        "#b7c0c7"
    )

    fun getColor(): Int {
        val colorText = colors[Random().nextInt(colors.size)]
        return Color.parseColor(colorText)
    }
}