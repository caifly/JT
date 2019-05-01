package caiflyy.pjy.today.widgets

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import caiflyy.pjy.today.database.weather.AirQuality
import caiflyy.pjy.today.database.weather.Weather
import caiflyy.pjy.today.utils.SizeUtils


/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.widgets
 * 日期：2019/4/14
 * 描述：控制质量自定义View
 * @author 蔡葳
 */
class AqiView:View{
    private var canRefresh = true

    private val paint: Paint = Paint()
    private val textPaint: Paint = Paint()
    private val textPaint2: Paint = Paint()

    private val roundColor: Int = 0 //圆环底色

    private val roundProgressColor: Int = 0 // 进度条颜色

    private val textColor: Int = 0 //文字颜色

    private var textSize: Float = 0.toFloat() //文字大小

    private val roundWidth = 28f //圆环宽度

    private var aqi: AirQuality? = null

    private var shader: Shader? = null

    private var speed: Float = 0.toFloat()

    private val aqiColors =
        intArrayOf(-0x9dffe2, -0x9432f9, -0x9432f9, -0x42fd7, -0x17800, -0x20000, -0x68fbac, -0x9dffe2)

    var currentAqiColor: Int = 0

    constructor(mContext: Context) : super(mContext,null)

    constructor(mContext: Context, attrs: AttributeSet) : super(mContext, attrs,0)

    constructor(mContext: Context, attrs: AttributeSet, defStyleAttr:Int) : super(mContext, attrs,defStyleAttr)

    init {
        textSize = SizeUtils.dp2px(12f).toFloat()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = roundWidth
        paint.strokeCap = Paint.Cap.ROUND
        paint.isAntiAlias = true
        textPaint.strokeWidth = 0F
        textPaint.isAntiAlias = true
        textPaint.color = Color.WHITE
        textPaint.textSize = textSize
        textPaint.textAlign = Paint.Align.CENTER
        textPaint2.isAntiAlias = true
        textPaint2.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (aqi == null) {
            return
        }
        paint.shader = null
        paint.color = -0x33777778
        val rectF: RectF
        if (height >= width) {
            rectF = RectF(0 + roundWidth, 0 + roundWidth, width - roundWidth, width - roundWidth)
        } else {
            rectF = RectF(
                0 + (width - height) / 2 + roundWidth,
                0 + roundWidth,
                width - (width - height) / 2 - roundWidth,
                height - roundWidth
            )
        }

        if (shader == null) {
            shader = SweepGradient(
                rectF.left + rectF.width() / 2,
                rectF.top + rectF.height() / 2,
                aqiColors,
                floatArrayOf(0.125f, 0.125f, 0.3f, 0.375f, 0.45f, 0.525f, 0.675f, 0.925f)
            )
            val matrix = Matrix()
            matrix.postRotate(45F, rectF.left + rectF.width() / 2, rectF.top + rectF.height() / 2)
            shader?.setLocalMatrix(matrix)
        }

        canvas.drawArc(rectF, 135F, 270F, false, paint)

        paint.shader = shader

        val aqiValue = Integer.parseInt(aqi!!.aqi)

        canvas.drawArc(rectF, 135F, aqiValue * speed / 500f * 270.0f, false, paint)

        val fontMetrics = textPaint.fontMetrics
        val top = fontMetrics.top//为基线到字体上边框的距离,即上图中的top
        val bottom = fontMetrics.bottom//为基线到字体下边框的距离,即上图中的bottom

        val baseLineY = (rectF.centerY() - top / 2 - bottom / 2).toInt()//基线中间点的y轴计算公式

        textPaint.color = Color.GRAY

        canvas.drawText(((aqiValue * speed).toInt()).toString(), rectF.centerX(), baseLineY.toFloat(), textPaint)

        val qltyStringWidth = textPaint.measureText(aqi?.qlty)

        val textRectf = RectF(
            width / 2 - qltyStringWidth / 1.5f,
            rectF.bottom,
            width / 2 + qltyStringWidth / 1.5f,
            rectF.bottom + rectF.width() / 4
        )

        textPaint2.color = currentAqiColor

        canvas.drawRoundRect(textRectf, 8F, 8F, textPaint2)

        val baseLineY2 = (textRectf.centerY() - top / 2 - bottom / 2).toInt()//基线中间点的y轴计算公式
        textPaint.color = Color.WHITE
        canvas.drawText(aqi?.qlty, textRectf.centerX(), baseLineY2.toFloat(), textPaint)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val desiredWidth = SizeUtils.dp2px(100f)
        val desiredHeight = SizeUtils.dp2px(120f)

        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        val width: Int
        val height: Int

        //Measure Width
        if (widthMode == View.MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize
        } else if (widthMode == View.MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize)
        } else {
            //Be whatever you want
            width = desiredWidth
        }

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
        setMeasuredDimension(width, height)
    }

    fun setApi(weather: Weather?) {
        if (weather == null || !canRefresh) {
            return
        }
        this.aqi = weather.airQuality
        val apiValue = Integer.parseInt(aqi!!.aqi)
        val targetColor: Int
        if (apiValue <= 50) {
            targetColor = aqiColors[2]
        } else if (apiValue <= 100) {
            targetColor = aqiColors[3]
        } else if (apiValue <= 150) {
            targetColor = aqiColors[4]
        } else if (apiValue <= 200) {
            targetColor = aqiColors[5]
        } else if (apiValue <= 300) {
            targetColor = aqiColors[6]
        } else {
            targetColor = aqiColors[7]
        }
        val animator = ValueAnimator.ofFloat(0F, 1F)
        animator.repeatCount = 0
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener { animation ->
            speed = animation.animatedValue as Float
            invalidate()
        }
        val animator2 = ValueAnimator.ofObject(ArgbEvaluator(), aqiColors[2], targetColor)
        animator2.interpolator = LinearInterpolator()
        animator2.repeatCount = 0
        animator2.addUpdateListener { animation -> currentAqiColor = animation.animatedValue as Int }

        val animSet = AnimatorSet()
        animSet.play(animator).with(animator2)
        animSet.duration = 2000
        animSet.start()

        canRefresh = false
        this.postDelayed({ canRefresh = true }, 2000)
    }
}