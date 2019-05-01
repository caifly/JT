package caiflyy.pjy.today.ui.technology

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.RecyclerView
import caiflyy.pjy.today.utils.SizeUtils



/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.utils
 * 日期：2019/4/24
 * 描述：文章的上下文菜单管理类
 * @author 蔡葳
 */
class ArticleContextMenuManager private constructor():RecyclerView.OnScrollListener(),View.OnAttachStateChangeListener {
    private var contextMenuView:ArticleContextMenu?=null
    private var isContextMenuDismissing: Boolean = false
    private var isContextMenuShowing: Boolean = false

    companion object {
        fun getInstance(): ArticleContextMenuManager {
            return Holder.instance
        }
    }

    private object Holder {
        val instance = ArticleContextMenuManager()
    }

    fun toggleContextMenuFromView(
        openingView: View,
        feedItem: Int,
        listener: ArticleContextMenu.OnArticleContextMenuItemClickListener
    ) {
        if (contextMenuView == null) {
            showContextMenuFromView(openingView, feedItem, listener)
        } else {
            hideContextMenu()
        }
    }

    private fun showContextMenuFromView(
        openingView: View,
        feedItem: Int,
        listener: ArticleContextMenu.OnArticleContextMenuItemClickListener
    ) {
        if (!isContextMenuShowing) {
            isContextMenuShowing = true
            contextMenuView = ArticleContextMenu(openingView.context)
            contextMenuView?.bindToItem(feedItem)
            contextMenuView?.addOnAttachStateChangeListener(this)
            contextMenuView?.setOnFeedMenuItemClickListener(listener)

            (openingView.rootView.findViewById<View>(android.R.id.content) as ViewGroup).addView(contextMenuView)

            contextMenuView?.getViewTreeObserver()!!.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    contextMenuView?.getViewTreeObserver()!!.removeOnPreDrawListener(this)
                    setupContextMenuInitialPosition(openingView)
                    performShowAnimation()
                    return false
                }
            })
        }
    }

    private fun setupContextMenuInitialPosition(openingView: View) {
        val openingViewLocation = IntArray(2)
        openingView.getLocationOnScreen(openingViewLocation)
        val additionalBottomMargin = SizeUtils.dp2px(16f)
        contextMenuView?.setTranslationX(openingViewLocation[0].toFloat() - contextMenuView?.getWidth()!! / 3)
        contextMenuView?.setTranslationY(openingViewLocation[1].toFloat() - contextMenuView?.getHeight()!! - additionalBottomMargin)
    }

    private fun performShowAnimation() {
        contextMenuView?.setPivotX(contextMenuView?.getWidth()!!.toFloat() / 2)
        contextMenuView?.setPivotY(contextMenuView?.getHeight()!!.toFloat())
        contextMenuView?.setScaleX(0.1f)
        contextMenuView?.setScaleY(0.1f)
        contextMenuView?.animate()
            ?.scaleX(1f)?.scaleY(1f)
            ?.setDuration(150)
            ?.setInterpolator(OvershootInterpolator())
            ?.setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    isContextMenuShowing = false
                }
            })
    }

    fun hideContextMenu() {
        if (!isContextMenuDismissing) {
            isContextMenuDismissing = true
            performDismissAnimation()
        }
    }

    private fun performDismissAnimation() {
        contextMenuView?.setPivotX(contextMenuView?.getWidth()!!.toFloat() / 2)
        contextMenuView?.setPivotY(contextMenuView?.getHeight()!!.toFloat())
        contextMenuView?.animate()
            ?.scaleX(0.1f)?.scaleY(0.1f)
            ?.setDuration(150)
            ?.setInterpolator(AccelerateInterpolator())
            ?.setStartDelay(100)
            ?.setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    if (contextMenuView != null) {
                        contextMenuView?.dismiss()
                    }
                    isContextMenuDismissing = false
                }
            })
    }

    override fun onViewDetachedFromWindow(v: View?) {
        contextMenuView = null
    }

    override fun onViewAttachedToWindow(v: View?) {
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (contextMenuView != null) {
            hideContextMenu()
            contextMenuView?.setTranslationY(contextMenuView?.getTranslationY()!!.toFloat() - dy);
        }
    }

}