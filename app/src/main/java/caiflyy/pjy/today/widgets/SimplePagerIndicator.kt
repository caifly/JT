package caiflyy.pjy.today.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager
import caiflyy.pjy.today.utils.SizeUtils







/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.widgets
 * 日期：2019/4/14
 * 描述：Pager指示器自定义View
 * @author 蔡葳
 */
class SimplePagerIndicator : View, ViewPager.OnPageChangeListener{
    private var mViewPager: ViewPager?=null
    private val roundPaint: Paint = Paint()
    private val selectedRoundPaint: Paint = Paint()
    private var textPaint: Paint = Paint()
    private val textSize: Int = SizeUtils.sp2px(18F)
    private val roundRadius: Float = 5F
    private val roundPadding = 10f
    private val titles: MutableList<String> = ArrayList<String>()

    var textBaseline: Float = 0.toFloat()
    var roundBaseline: Float = 0.toFloat()

    private var selectedPosition = 0

    private var positionOffset = 0.0f

    var textWidth: Float = 0.toFloat()

    constructor(mContext: Context) : super(mContext,null)

    constructor(mContext: Context, attrs: AttributeSet) : super(mContext, attrs,0)

    constructor(mContext: Context, attrs: AttributeSet, defStyleAttr:Int) : super(mContext, attrs,defStyleAttr)

    init {
        roundPaint.style = Paint.Style.FILL_AND_STROKE;
        roundPaint.strokeWidth = 2F
        roundPaint.color = Color.WHITE
        roundPaint.isAntiAlias = true
        roundPaint.alpha = 100

        selectedRoundPaint.style = Paint.Style.FILL_AND_STROKE
        selectedRoundPaint.strokeWidth = 2F
        selectedRoundPaint.color = Color.WHITE
        selectedRoundPaint.isAntiAlias = true

        textPaint.strokeWidth = 0F
        textPaint.isAntiAlias = true
        textPaint.color = Color.WHITE
        textPaint.textSize = textSize.toFloat()
        textPaint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val count = titles.size
        if (count == 0) {
            return
        }
        textBaseline = height / 2 - textPaint.fontMetrics.top / 2 - textPaint.fontMetrics.bottom / 2
        roundBaseline = textBaseline + roundRadius + 20

        val roundTotalWidth = 2 * roundRadius * count + (count - 1) * roundPadding
        val startX = width / 2 - roundTotalWidth / 2 + roundRadius

        for (i in 0 until count) {
            canvas.drawCircle(startX + 2 * roundRadius * i + roundPadding * i, roundBaseline, roundRadius, roundPaint)
        }

        val offSetX = (roundPadding + 2 * roundRadius) * positionOffset

        canvas.drawCircle(
            startX + 2 * roundRadius * selectedPosition + roundPadding * selectedPosition + offSetX,
            roundBaseline,
            roundRadius,
            selectedRoundPaint
        )

        if (selectedPosition < titles.size - 1) {
            textWidth =
                textPaint.measureText(titles[selectedPosition]) + textPaint.measureText(titles[selectedPosition + 1])
            textPaint.alpha = (255 * (1 - Math.abs(positionOffset))) as Int
            canvas.drawText(
                titles[selectedPosition],
                width / 2 - textWidth / 2 * positionOffset,
                textBaseline,
                textPaint
            )
            textPaint.alpha = (255 * Math.abs(positionOffset)) as Int
            canvas.drawText(
                titles[selectedPosition + 1],
                width / 2 + textWidth / 2 - textWidth / 2 * positionOffset,
                textBaseline,
                textPaint
            )
        } else {
            textPaint.alpha = (255 * (1 - Math.abs(positionOffset))).toInt()
            canvas.drawText(
                titles[selectedPosition],
                width / 2 - textWidth / 2 * positionOffset,
                textBaseline,
                textPaint
            )
        }
    }
    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (position >= selectedPosition) {
            // 1 -> 2 -> 3 滑动
            this.positionOffset = positionOffset;
        } else if (position < selectedPosition) {
            // 1 <- 2 <- 3 滑动
            this.positionOffset = -(1 - positionOffset);
        }
        invalidate();
    }

    override fun onPageSelected(position: Int) {
        selectedPosition = position;
        invalidate();
    }

    fun setViewPager(view:ViewPager ){
        if (mViewPager == view) {
            return
        }
        mViewPager = view
        view.addOnPageChangeListener(this)
        notifyDataSetChanged()
    }

    fun notifyDataSetChanged(){
        selectedPosition = 0
        titles.clear()
        val adapter = mViewPager?.adapter
        val count = adapter?.count
        for (i in 0 until count!!) {
            titles.add(adapter.getPageTitle(i)!!.toString())
        }
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val desiredWidth = SizeUtils.dp2px(100f)
        val desiredHeight = SizeUtils.dp2px(48f)

        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        val width: Int
        val height: Int

        //Measure Width
        width = when (widthMode) {
            View.MeasureSpec.EXACTLY -> //Must be this size
                widthSize
            View.MeasureSpec.AT_MOST -> //Can't be bigger than...
                Math.min(desiredWidth, widthSize)
            MeasureSpec.UNSPECIFIED-> desiredWidth
            else -> //Be whatever you want
                desiredWidth
        }
        //Measure Height
        height = when (heightMode) {
            View.MeasureSpec.EXACTLY -> //Must be this size
                heightSize
            View.MeasureSpec.AT_MOST -> //Can't be bigger than...
                Math.min(desiredHeight, heightSize)
            MeasureSpec.UNSPECIFIED-> desiredWidth
            else -> //Be whatever you want
                desiredHeight
        }
        //MUST CALL THIS
        setMeasuredDimension(width, height)
    }
}