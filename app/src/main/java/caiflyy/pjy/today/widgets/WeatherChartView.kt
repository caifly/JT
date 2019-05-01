package caiflyy.pjy.today.widgets

import android.animation.LayoutTransition
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import caiflyy.pjy.today.R
import caiflyy.pjy.today.data.weather.WeatherBean
import caiflyy.pjy.today.database.weather.ForecastDaily
import caiflyy.pjy.today.database.weather.Weather
import caiflyy.pjy.today.utils.*
import caiflyy.pjy.today.utils.image.ImageLoaderManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.widgets
 * 日期：2019/4/14
 * 描述：天气图表自定义View
 * @author 蔡葳
 */
class WeatherChartView: LinearLayout, KoinComponent {
    private val imageLoaderManager:ImageLoaderManager by inject()
    private var canRefresh = true

    private val dailyForecastList = ArrayList<ForecastDaily>()

    var cellParams: LinearLayout.LayoutParams? = null
    var rowParams: LinearLayout.LayoutParams? = null
    var chartParams: LinearLayout.LayoutParams? = null

    var transition = LayoutTransition()

    constructor(mContext: Context) : super(mContext,null)

    constructor(mContext: Context, attrs: AttributeSet) : super(mContext, attrs,0)

    constructor(mContext: Context, attrs: AttributeSet, defStyleAttr:Int) : super(mContext, attrs,defStyleAttr)

    init {
        this.orientation = LinearLayout.VERTICAL
        transition.enableTransitionType(LayoutTransition.APPEARING)
        this.layoutTransition = transition
        rowParams =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        cellParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        chartParams =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    private fun letItGo() {
        removeAllViews()
        val dateTitleView = LinearLayout(context)
        dateTitleView.layoutParams = rowParams
        dateTitleView.orientation = LinearLayout.HORIZONTAL
        dateTitleView.layoutTransition = transition
        dateTitleView.removeAllViews()

        val iconView = LinearLayout(context)
        iconView.layoutParams = rowParams
        iconView.orientation = LinearLayout.HORIZONTAL
        iconView.layoutTransition = transition
        iconView.removeAllViews()

        val weatherStrView = LinearLayout(context)
        weatherStrView.layoutParams = rowParams
        weatherStrView.orientation = LinearLayout.HORIZONTAL
        weatherStrView.layoutTransition = transition
        weatherStrView.removeAllViews()

        val minTemp = ArrayList<Int>()
        val maxTemp = ArrayList<Int>()
        for (i in 0 until dailyForecastList.size) {
            val tvDate = TextView(context)
            tvDate.gravity = Gravity.CENTER
            tvDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
            tvDate.setTextColor(resources.getColor(R.color.colorTextDark))
            tvDate.visibility = View.INVISIBLE
            val tvWeather = TextView(context)
            tvWeather.gravity = Gravity.CENTER
            tvWeather.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
            tvWeather.setTextColor(resources.getColor(R.color.colorTextDark))
            tvWeather.visibility = View.INVISIBLE
            val ivIcon = ImageView(context)
            ivIcon.setAdjustViewBounds(true)
            ivIcon.setScaleType(ImageView.ScaleType.FIT_CENTER)
            val padding = SizeUtils.dp2px(10f)
            val width = ScreenUtils.getScreenWidth() / dailyForecastList.size
            val ivParam = LinearLayout.LayoutParams(width, width)
            ivParam.weight = 1f
            ivIcon.layoutParams = ivParam
            ivIcon.setPadding(padding, padding, padding, padding)
            ivIcon.visibility = View.INVISIBLE
            val week = getWeek(dailyForecastList[i].date, DATE_SDF)
            tvDate.text = week
            tvWeather.text = dailyForecastList[i].cond_txt_d
            WeatherUtil
                .getInstance(context)
                .getWeatherDict(dailyForecastList[i].cond_code_d)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<WeatherBean> {
                    @Throws(Exception::class)
                    override fun accept(weatherBean: WeatherBean) {
                        imageLoaderManager.displayImage(weatherBean.icon, ivIcon)
                    }
                })
            minTemp.add(Integer.valueOf(dailyForecastList[i].tmp_min))
            maxTemp.add(Integer.valueOf(dailyForecastList[i].tmp_max))
            weatherStrView.addView(tvWeather, cellParams)
            dateTitleView.addView(tvDate, cellParams)
            iconView.addView(ivIcon)
            this.postDelayed({
                tvDate.visibility = View.VISIBLE
                tvWeather.visibility = View.VISIBLE
                ivIcon.setVisibility(View.VISIBLE)
            }, (200 * i).toLong())
        }
        addView(dateTitleView)
        addView(iconView)
        addView(weatherStrView)
        val chartView = ChartView(context)
        chartView.setData(minTemp, maxTemp)
        chartView.setPadding(0, SizeUtils.dp2px(16f), 0, SizeUtils.dp2px(16f))
        addView(chartView, chartParams)
    }

    fun setWeather(weather: Weather?) {
        if (weather == null || !canRefresh) {
            return
        }
        dailyForecastList.clear()
        dailyForecastList.addAll(weather.forecastDaily)
        letItGo()
        canRefresh = false
        this.postDelayed({ canRefresh = true }, (weather.forecastDaily.size * 200).toLong())
    }
}