package caiflyy.pjy.today.ui.study

import caiflyy.pjy.today.R
import caiflyy.pjy.today.data.weather.StudyClassification
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.weather
 * 日期：2019/4/15
 * 描述：每小时天气信息适配器
 * @author 蔡葳
 */
class StudyCourseAdapter(layoutResId: Int, data: MutableList<StudyClassification>?) :
    BaseQuickAdapter<StudyClassification, BaseViewHolder>(layoutResId, data){

    override fun convert(holder: BaseViewHolder?, item: StudyClassification?) {
        holder?.setText(R.id.tv_course_item_title, item?.classificationName)
        holder?.setText(R.id.tv_course_item_subtitle, item?.imgPath)
        holder?.setText(R.id.tv_course_item_desc,"[img src=ic_testimonial_quote_start/] ${item?.desc} [img src=ic_testimonial_quote_end/]")
    }
}