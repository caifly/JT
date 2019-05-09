package caiflyy.pjy.today.ui.demo

import java.util.*


/**
 * 项目名称：S0402
 * 包名：caiflyy.pjy.s0402
 * 日期：2019/1/24
 * 描述：
 * @author 蔡葳
 */
class Game() {
    private object Holder { val INSTANCE = Game() }

    companion object {
        const val NORMAL_LEVEL = 50
        const val HARD_LEVEL = 100
        const val WIN = 10
        const val FAIL = 20
        val instance: Game by lazy { Holder.INSTANCE }
    }
    private var randomNumber:Int=0
    var gameLevel:Int=0
    var result = 0
    lateinit var title: String

    fun start(level:Int) {
        randomNumber = Random().nextInt(level)
        title = "已产生0-${level}的随机数字，请竞猜"
        gameLevel = level
    }

    fun compare(inputNumber:Int):String{
        return when {
            inputNumber>randomNumber -> "猜大了"
            inputNumber<randomNumber -> "猜小了"
            else -> "win"
        }
    }
}