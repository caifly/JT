package caiflyy.pjy.today.ui.weather

import android.text.TextUtils
import caiflyy.pjy.today.R
import caiflyy.pjy.today.data.weather.AqiDetailBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.koin.core.KoinComponent


/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.weather
 * 日期：2019/4/15
 * 描述：每小时天气信息适配器
 * @author 蔡葳
 */
class AqiAdapter(layoutResId: Int, data: MutableList<AqiDetailBean>?) :
    BaseQuickAdapter<AqiDetailBean, BaseViewHolder>(layoutResId, data), KoinComponent {

    override fun convert(holder: BaseViewHolder?, item: AqiDetailBean?) {
        holder?.setText(R.id.tv_aqi_name, item?.name)
        holder?.setText(R.id.tv_aqi_desc, item?.desc)
        holder?.setText(R.id.tv_aqi_value, item?.value)
        val value = if (TextUtils.isDigitsOnly(item?.value)) item?.value?.toInt() else 0
        if (value != null) when (value) {
            50 -> holder?.setBackgroundColor(R.id.view_aqi_qlty, -0x9432f9)
            100 -> holder?.setBackgroundColor(R.id.view_aqi_qlty, -0x42fd7)
            150 -> holder?.setBackgroundColor(R.id.view_aqi_qlty, -0x17800)
            200 -> holder?.setBackgroundColor(R.id.view_aqi_qlty, -0x20000)
            300 -> holder?.setBackgroundColor(R.id.view_aqi_qlty, -0x68fbac)
            else -> holder?.setBackgroundColor(R.id.view_aqi_qlty, -0x9dffe2)
        }
    }
}