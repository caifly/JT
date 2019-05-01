package caiflyy.pjy.today.ui.technology

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import caiflyy.pjy.today.R
import caiflyy.pjy.today.utils.SizeUtils


/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.technology
 * 日期：2019/4/24
 * 描述：文章的上下文菜单类
 * @author 蔡葳
 */
class ArticleContextMenu(context: Context?) : LinearLayout(context) {
    private val CONTEXT_MENU_WIDTH = SizeUtils.dp2px(240f)
    private var feedItem = -1
    lateinit var onItemClickListener: OnArticleContextMenuItemClickListener

    init {
        val view = LayoutInflater.from(getContext()).inflate(R.layout.layout_article_item_context_menu, this, true)
        setBackgroundResource(R.drawable.dot9_technology_item_context_menu_bg_container_shadow)
        orientation = LinearLayout.VERTICAL
        layoutParams = LinearLayout.LayoutParams(CONTEXT_MENU_WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.findViewById<Button>(R.id.btnReport).setOnClickListener {
            onReportClick()
        }
        view.findViewById<Button>(R.id.btnSharePhoto).setOnClickListener {
            onSharePhotoClick()
        }
        view.findViewById<Button>(R.id.btnCopyShareUrl).setOnClickListener {
            onCopyShareUrlClick()
        }
        view.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            onCancelClick()
        }
    }

    fun bindToItem(feedItem: Int) {
        this.feedItem = feedItem
    }


    fun dismiss() {
        (parent as ViewGroup).removeView(this)
    }

    fun onReportClick() {
        if (onItemClickListener != null) {
            onItemClickListener.onReportClick(feedItem)
        }
    }

    fun onSharePhotoClick() {
        if (onItemClickListener != null) {
            onItemClickListener.onSharePhotoClick(feedItem)
        }
    }

    fun onCopyShareUrlClick() {
        if (onItemClickListener != null) {
            onItemClickListener.onCopyShareUrlClick(feedItem)
        }
    }

    fun onCancelClick() {
        if (onItemClickListener != null) {
            onItemClickListener.onCancelClick(feedItem)
        }
    }

    fun setOnFeedMenuItemClickListener(onItemClickListener: OnArticleContextMenuItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }


    interface OnArticleContextMenuItemClickListener {
        /**
         * 上下文菜单条目点击回调
         * @param feedItem 点击的菜单条目
         */
        fun onReportClick(feedItem: Int)

        /**
         * 上下文菜单条目点击回调
         * @param feedItem 点击的菜单条目
         */
        fun onSharePhotoClick(feedItem: Int)

        /**
         * 上下文菜单条目点击回调
         * @param feedItem 点击的菜单条目
         */
        fun onCopyShareUrlClick(feedItem: Int)

        /**
         * 上下文菜单条目点击回调
         * @param feedItem 点击的菜单条目
         */
        fun onCancelClick(feedItem: Int)
    }
}