package caiflyy.pjy.today.utils.image

import android.widget.ImageView

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.utils.image
 * 日期：2019/4/15
 * 描述：网络图片加载管理类
 * @author 蔡葳
 */
class ImageLoaderManager(private val imageLoader:ImageLoaderApi) {
    companion object {
        @Volatile
        var instance: ImageLoaderManager? = null

        fun getInstance(imageLoader:ImageLoaderApi): ImageLoaderManager {
            if (instance == null) {
                synchronized(ImageLoaderManager::class) {
                    if (instance == null) {
                        instance = ImageLoaderManager(imageLoader)
                    }
                }
            }
            return instance!!
        }
    }

    fun displayImage(url: String, imageView: ImageView) {
        imageLoader.displayImage(url, imageView)
    }
}