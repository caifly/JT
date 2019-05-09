package caiflyy.pjy.today.utils.image

import android.widget.ImageView
import caiflyy.pjy.today.R
import com.squareup.picasso.Picasso

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.utils.image
 * 日期：2019/4/15
 * 描述：Picasso图片加载库具体实现类
 * @author 蔡葳
 */
class PicassoImageLoader:ImageLoaderApi {
    override fun displayImage(url: String, imageView: ImageView) {
        Picasso.get()
            .load(url)
            .error(R.drawable.vector_img_error)
            .into(imageView);
    }
}