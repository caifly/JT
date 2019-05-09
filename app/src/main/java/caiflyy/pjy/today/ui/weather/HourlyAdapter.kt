package caiflyy.pjy.today.ui.weather

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import caiflyy.pjy.today.R
import caiflyy.pjy.today.database.weather.ForecastHourly
import caiflyy.pjy.today.utils.*
import caiflyy.pjy.today.utils.image.ImageLoaderManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.core.KoinComponent
import org.koin.core.inject


/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.weather
 * 日期：2019/4/15
 * 描述：每小时天气信息适配器
 * @author 蔡葳
 */
class HourlyAdapter(layoutResId: Int, data: MutableList<ForecastHourly>?) :
    BaseQuickAdapter<ForecastHourly, BaseViewHolder>(layoutResId, data), KoinComponent {

    val imageLoaderManager:ImageLoaderManager by inject()

    override fun convert(holder: BaseViewHolder?, item: ForecastHourly?) {
        val width = ScreenUtils.getScreenWidth() / 5
        val params = holder?.itemView?.layoutParams as RecyclerView.LayoutParams
        params.width = width
        holder.itemView.layoutParams = params
        holder.setText(R.id.tv_hourly_temp, item?.tmp + "°")
        val time = string2String(item?.time!!, HOURLY_FORECAST_SDF, HOUR_SDF)
        holder.setText(R.id.tv_hourly_time, time)
        val imageView = holder.getView<ImageView>(R.id.iv_hourly_weather)

        WeatherUtil
            .getInstance(mContext)
            .getWeatherDict(item.cond_code)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { weatherBean -> imageLoaderManager.displayImage(weatherBean.icon, imageView) }
    }
}