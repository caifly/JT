package caiflyy.pjy.today.ui.demo

import kotlin.math.sqrt

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.demo
 * 日期：2019-05-08
 * 描述：
 * @author 蔡葳
 */
data class Demo12Vector(val x: Double, val y: Double){
    private val magnitude: Double
    get() = sqrt(x*x + y*y)

    operator fun plus(other: Demo12Vector) = Demo12Vector(x + other.x, y + other.y)
    operator fun minus(other: Demo12Vector) = Demo12Vector(x - other.x, y - other.y)
    operator fun times(magnitude: Double): Demo12Vector {
        return Demo12Vector(x * magnitude, y * magnitude)
    }

    fun unitVector(): Demo12Vector? {
        if (magnitude > 0.0) {
            return Demo12Vector(x / magnitude, y / magnitude)
        }
        return null
    }
}