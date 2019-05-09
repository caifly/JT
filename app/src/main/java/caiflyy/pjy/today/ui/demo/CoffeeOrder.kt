package caiflyy.pjy.today.ui.demo

/**
 * 项目名称：S0401
 * 包名：caiflyy.pjy.s0401
 * 日期：2019/1/24
 * 描述：
 * @author 蔡葳
 */
data class CoffeeOrder(val title:String="咖啡20元一杯\n甜品10元一份",
                       var count:String="1",
                       val dessert: Array<Boolean> = arrayOf(false,false,false)) {
}