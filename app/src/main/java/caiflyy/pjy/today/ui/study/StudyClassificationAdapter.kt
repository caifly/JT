package caiflyy.pjy.today.ui.study

import android.widget.ImageView
import caiflyy.pjy.today.R
import caiflyy.pjy.today.data.weather.StudyClassification
import caiflyy.pjy.today.utils.image.ImageLoaderManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.koin.core.KoinComponent
import org.koin.core.inject


/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.weather
 * 日期：2019/4/15
 * 描述：每小时天气信息适配器
 * @author 蔡葳
 */
class StudyClassificationAdapter(layoutResId: Int, data: MutableList<StudyClassification>?) :
    BaseQuickAdapter<StudyClassification, BaseViewHolder>(layoutResId, data), KoinComponent {
    val imageLoaderManager:ImageLoaderManager by inject()

    override fun convert(holder: BaseViewHolder?, item: StudyClassification?) {
        holder?.setText(R.id.tv_study_classification_item_name, item?.classificationName)
        val img = holder?.getView<ImageView>(R.id.img_study_classification_item)
        imageLoaderManager.displayImage(item?.imgPath!!,img!!)
    }
}