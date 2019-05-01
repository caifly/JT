package caiflyy.pjy.today.ui.technology

import android.widget.ImageView
import caiflyy.pjy.today.R
import caiflyy.pjy.today.cloud.article.NewsComments
import caiflyy.pjy.today.utils.image.ImageLoaderManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*


/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.technology
 * 日期：2019/4/15
 * 描述：文章信息适配器
 * @author 蔡葳
 */
class CommentAdapter(layoutResId: Int, data: MutableList<NewsComments>?) :
    BaseQuickAdapter<NewsComments, BaseViewHolder>(layoutResId, data), KoinComponent {

    private val imageLoaderManager: ImageLoaderManager by inject()

    override fun convert(holder: BaseViewHolder?, item: NewsComments?) {
        val sdf = SimpleDateFormat("E, yyyy年 MMM dd日 HH:mm", Locale.getDefault())
        holder?.setText(R.id.tv_comments_time, sdf.format(item?.data))
        holder?.setText(R.id.tv_comments_username, item?.commentUserName)
        holder?.setText(R.id.tv_comments_content, item?.newsCommentContent)
        val icon = holder?.getView<ImageView>(R.id.img_comments_portrait)
        imageLoaderManager.displayImage(item?.commentPortraitPath!!,icon!!)
    }
}