package caiflyy.pjy.today.widgets

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import caiflyy.pjy.today.R
import caiflyy.pjy.today.utils.SizeUtils


/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.widgets
 * 日期：2019/4/14
 * 描述：图表自定义View
 * @author 蔡葳
 */
class ChartView : View {
    private val paint: Paint = Paint()

    private var minTemp = ArrayList<Int>()
    private var maxTemp = ArrayList<Int>()

    private var minValue = 0
    private var maxValue = 0

    var minPath: Path = Path()
    var maxPath: Path = Path()

    private var textMargin: Int = 0

    private var mAnimatorValue: Float = 0.toFloat()

    private val LINE_COLOR = -0x1e1a18

    constructor(mContext: Context) : super(mContext, null)

    constructor(mContext: Context, attrs: AttributeSet) : super(mContext, attrs, 0)

    constructor(mContext: Context, attrs: AttributeSet, defStyleAttr: Int) : super(mContext, attrs, defStyleAttr)

    init {
        paint.isAntiAlias = true
        paint.strokeCap = Paint.Cap.ROUND
        paint.textSize = SizeUtils.sp2px(10F).toFloat()
        textMargin = SizeUtils.dp2px(8f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()

        val heightScale = (height / (maxValue - minValue)).toInt()
        val widthtScale = (width / (2 * minTemp.size)).toInt()

        paint.strokeWidth = widthtScale.toFloat() / 3

        val rect = Rect()
        for (i in 0 until minTemp.size) {
            paint.style = Paint.Style.STROKE
            paint.color = LINE_COLOR
            canvas.drawLine(
                (2 * i + 1) * widthtScale.toFloat(),
                (maxValue - minTemp[i]) * heightScale * mAnimatorValue,
                (2 * i + 1) * widthtScale.toFloat(),
                (maxValue - maxTemp.get(i)) * heightScale * mAnimatorValue,
                paint
            )
            paint.style = Paint.Style.FILL
            paint.color = context.resources.getColor(R.color.colorTextDark2nd)
            val valueStrMin = minTemp[i].toString()
            paint.getTextBounds(valueStrMin, 0, valueStrMin.length, rect)
            canvas.drawText(
                "$valueStrMin°",
                (2 * i + 1) * widthtScale - rect.width().toFloat() / 2,
                (maxValue - minTemp.get(i)) * heightScale * mAnimatorValue + rect.height() + textMargin,
                paint
            )
            val valueStrMax = maxTemp.get(i).toString()
            paint.getTextBounds(valueStrMax, 0, valueStrMax.length, rect)
            canvas.drawText(
                "$valueStrMax°",
                (2 * i + 1) * widthtScale - rect.width().toFloat() / 2,
                (maxValue - maxTemp[i]) * heightScale * mAnimatorValue - textMargin,
                paint
            )
        }

    }

    fun setData(minTemp: ArrayList<Int>, maxTemp: ArrayList<Int>) {
        this.minTemp = minTemp
        this.maxTemp = maxTemp
        minValue = minTemp[0]
        maxValue = maxTemp[0]
        for (i in minTemp) {
            if (i < minValue) {
                minValue = i
            }
        }
        for (n in maxTemp) {
            if (n > maxValue) {
                maxValue = n
            }
        }
        minValue = minValue - 3
        maxValue = maxValue + 3
        val valueAnimator = ValueAnimator.ofFloat(0F, 1F)
        valueAnimator.addUpdateListener { valueAnimator ->
            mAnimatorValue = valueAnimator.animatedValue as Float
            invalidate()
        }
        valueAnimator.duration = ((minTemp.size + 1) * 200).toLong()
        valueAnimator.repeatCount = 0
        valueAnimator.start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val desiredHeight = SizeUtils.dp2px(140f)

        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        val height: Int

        //Measure Height
        if (heightMode == View.MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize
        } else if (heightMode == View.MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize)
        } else {
            //Be whatever you want
            height = desiredHeight
        }

        //MUST CALL THIS
        setMeasuredDimension(widthSize, height)
    }
}