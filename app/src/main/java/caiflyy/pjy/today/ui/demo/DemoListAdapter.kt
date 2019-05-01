package caiflyy.pjy.today.ui.demo

import caiflyy.pjy.today.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.koin.core.KoinComponent


/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.demo
 * 日期：2019/4/15
 * 描述：示例列表信息适配器
 * @author 蔡葳
 */
class DemoListAdapter(layoutResId: Int, data: MutableList<DemoItem>?) :
    BaseQuickAdapter<DemoItem, BaseViewHolder>(layoutResId, data), KoinComponent {

    override fun convert(holder: BaseViewHolder?, item: DemoItem?) {
        holder?.setText(R.id.tvDemoTitle, item?.title!!)
        holder?.setText(R.id.tvDemoDesc, item?.desc!!)
        holder?.setImageResource(R.id.imgDemoIcon, item?.icon!!)
    }
}