package caiflyy.pjy.today.utils.image

import android.widget.ImageView

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.utils.image
 * 日期：2019/4/15
 * 描述：网络图片加载接口
 * @author 蔡葳
 */
interface ImageLoaderApi {
    fun displayImage(url: String, imageView: ImageView)
}