package caiflyy.pjy.today.ui.demo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.max

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.demo
 * 日期：2019-05-08
 * 描述：
 * @author 蔡葳
 */
class Demo12ViewModel :ViewModel(){
    private var sizeX = 0.0
    private var sizeY = 0.0
    private var origin: Demo12Vector = Demo12Vector(sizeX / 2.0, sizeY / 2.0)
    private var starVectorMagnitude = max(sizeX, sizeY)
    private var timer = Timer("Stars", false)
    private val random=Random()

    val starLiveData = MutableLiveData<Demo12Star>()
    val emittingLiveData = MutableLiveData<Boolean>()

    fun startEmittingStars() {
        emittingLiveData.postValue(true)
        // Emit a new star every 20 milliseconds
        timer.schedule(0, 20) {
            // Choose random start point
            val x = rand(0, sizeX.toInt()).toDouble()
            val y = rand(0, sizeY.toInt()).toDouble()
            val starEndPosition = calculateStarEndPosition(x, y)

            val star = Demo12Star(x.toInt(), y.toInt(), starEndPosition.x.toInt(), starEndPosition.y.toInt())

            starLiveData.postValue(star)
        }
    }

    fun stopEmittingStars() {
        emittingLiveData.postValue(false)
        timer.cancel()
        timer = Timer("Stars", false)
    }

    fun setupDisplay(sizeX: Double, sizeY: Double) {
        this.sizeX = sizeX
        this.sizeY = sizeY
        origin = Demo12Vector(sizeX / 2.0, sizeY / 2.0)
        starVectorMagnitude = max(sizeX, sizeY)
    }

    private fun calculateStarEndPosition(x: Double, y: Double): Demo12Vector {
        // Get end position as vector from origin of magnitude starVectorMagnitude
        // and in the same direction as (x, y).
        // If unitVector is null, then just use original position.
        val position = Demo12Vector(x, y) - origin
        val unitVector = position.unitVector()
        return if (unitVector != null) {
            unitVector * starVectorMagnitude + origin
        } else {
            position + origin
        }
    }

    fun rand(from: Int, to: Int): Int {
        return random.nextInt(to - from) + from
    }
}