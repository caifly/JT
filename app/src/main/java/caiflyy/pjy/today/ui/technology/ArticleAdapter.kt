package caiflyy.pjy.today.ui.technology

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.animation.AccelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextSwitcher
import caiflyy.pjy.today.R
import caiflyy.pjy.today.cloud.article.ArticleBean
import caiflyy.pjy.today.utils.image.ImageLoaderManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.technology
 * 日期：2019/4/15
 * 描述：文章信息适配器
 * @author 蔡葳
 */
class ArticleAdapter(layoutResId: Int, data: MutableList<ArticleBean>?) :
    BaseQuickAdapter<ArticleBean, BaseViewHolder>(layoutResId, data), KoinComponent {
    private val accelerateInterpolator = AccelerateInterpolator()
    private val overshootInterpolator = OvershootInterpolator(4f)
    lateinit var onItemButtonClickListener: OnItemButtonClickListener
    private val imageLoaderManager: ImageLoaderManager by inject()

    override fun convert(holder: BaseViewHolder?, item: ArticleBean?) {
        holder?.setText(R.id.tvItemNewTechnologyTitle, item?.title)
        holder?.setText(R.id.tv_news_item_content, item?.content)
        val tsLikesCounter = holder!!.getView<TextSwitcher>(R.id.tsLikesCounter)
        tsLikesCounter.setText(
            tsLikesCounter.resources.getQuantityString(
                R.plurals.likes_count,
                item!!.likeCount,
                item.likeCount
            )
        )
        val imageView = holder.getView<ImageView>(R.id.img_news_item)
        imageLoaderManager.displayImage(item.imgPath, imageView)
        holder.getView<ImageButton>(R.id.btnNewsItemLike).let { it ->
            if (item.isLike){
                it.setImageResource(R.drawable.img_technology_item_ic_heart_red)
            }else{
                it.setImageResource(R.drawable.img_technology_item_ic_heart_outline_grey)
                it.setOnClickListener {
                    animateHeartButton(it as ImageButton)
                    val tsLikesCounter = holder.getView<TextSwitcher>(R.id.tsLikesCounter)
                    updateLikesCounter(tsLikesCounter, item.likeCount+1)
                    onItemButtonClickListener.onLike(item)
                }
            }

        }
        holder.getView<ImageButton>(R.id.btnNewsItemComments).setOnClickListener {
            val startingLocation = IntArray(2)
            it.getLocationOnScreen(startingLocation)
            onItemButtonClickListener.onComment(startingLocation[1], item.objectId)
        }
        holder.getView<ImageButton>(R.id.btnNewsItemMore).setOnClickListener {
            ArticleContextMenuManager.getInstance()
                .toggleContextMenuFromView(
                    it,
                    data.indexOf(item),
                    object : ArticleContextMenu.OnArticleContextMenuItemClickListener {
                        override fun onReportClick(feedItem: Int) {
                            ArticleContextMenuManager.getInstance().hideContextMenu()
                            onItemButtonClickListener.onReport(feedItem)
                        }

                        override fun onSharePhotoClick(feedItem: Int) {
                            ArticleContextMenuManager.getInstance().hideContextMenu()
                            onItemButtonClickListener.onPhoto(feedItem)
                        }

                        override fun onCopyShareUrlClick(feedItem: Int) {
                            ArticleContextMenuManager.getInstance().hideContextMenu()
                            onItemButtonClickListener.onShared(feedItem)
                        }

                        override fun onCancelClick(feedItem: Int) {
                            ArticleContextMenuManager.getInstance().hideContextMenu()
                        }
                    })
        }
    }

    private fun animateHeartButton(imageButton: ImageButton) {
        val animatorSet = AnimatorSet()

        val rotationAnim = ObjectAnimator.ofFloat(
            imageButton, "rotation", 0f, 360f
        )
        rotationAnim.duration = 300
        rotationAnim.interpolator = accelerateInterpolator

        val bounceAnimX = ObjectAnimator.ofFloat(
            imageButton, "scaleX", 0.2f, 1f
        )
        bounceAnimX.duration = 300
        bounceAnimX.interpolator = overshootInterpolator

        val bounceAnimY = ObjectAnimator.ofFloat(
            imageButton, "scaleY", 0.2f, 1f
        )
        bounceAnimY.duration = 300
        bounceAnimY.interpolator = overshootInterpolator
        bounceAnimY.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                imageButton.setImageResource(R.drawable.img_technology_item_ic_heart_red)
            }

            override fun onAnimationEnd(animation: Animator) {

            }
        })
        animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim)
        animatorSet.start()
    }

    private fun updateLikesCounter(pTextSwither: TextSwitcher, toValue: Int) {
        val likesCountTextFrom = pTextSwither.resources.getQuantityString(
            R.plurals.likes_count, toValue - 1, toValue - 1
        )
        pTextSwither.setCurrentText(likesCountTextFrom)

        val likesCountTextTo = pTextSwither.resources.getQuantityString(
            R.plurals.likes_count, toValue, toValue
        )
        pTextSwither.setText(likesCountTextTo)
    }

    interface OnItemButtonClickListener {
        fun onLike(articleBean: ArticleBean)
        fun onComment(locationY: Int, itemId: String)
        fun onReport(itemId: Int)
        fun onPhoto(itemId: Int)
        fun onShared(itemId: Int)
    }
}