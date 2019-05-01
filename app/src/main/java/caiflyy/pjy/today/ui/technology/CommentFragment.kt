package caiflyy.pjy.today.ui.technology


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import caiflyy.pjy.today.R
import caiflyy.pjy.today.utils.EXTRA_KEY_ITEM_ID
import caiflyy.pjy.today.utils.EXTRA_KEY_LOCATION_Y
import caiflyy.pjy.today.utils.TAG
import caiflyy.pjy.today.widgets.SendCommentButton
import kotlinx.android.synthetic.main.fragment_comment.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.support.v4.toast
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.technology
 * 日期：2019/4/15
 * 描述：技术文章评论界面
 * @author 蔡葳
 */
class CommentFragment : Fragment() {
    private val viewModel:CommentsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_comment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        val locationY = bundle?.getInt(EXTRA_KEY_LOCATION_Y)
        val itemId = bundle?.getString(EXTRA_KEY_ITEM_ID)
        if (savedInstanceState==null){
            contentRoot.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    contentRoot.viewTreeObserver.removeOnPreDrawListener(this)
                    startIntroAnimation(locationY!!)
                    return true
                }
            })
        }
        rvComments.layoutManager = LinearLayoutManager(activity)
        val commentAdapter = CommentAdapter(R.layout.item_comments, null)
        rvComments.adapter = commentAdapter
        viewModel.onSuccess().observe(this, Observer {
            AnkoLogger(TAG).error("评论信息观察者")
            commentAdapter.setNewData(it)
        })
        btnSendComment.setOnSendClickListener {
            if (etComment.text.isEmpty()){
                toast("请输入评论内容")
            }else{
                val content = etComment.text.toString().trim()
                viewModel.addComment(itemId!!,content)
                etComment.text=null
                btnSendComment.setCurrentState(SendCommentButton.STATE_DONE)

            }
        }
        viewModel.getComments(itemId)
    }

    /**
     * 绘制进入动画
     */
    private fun startIntroAnimation(drawingStartLocation:Int) {
        contentRoot.scaleY = 0.1f
        contentRoot.pivotY = drawingStartLocation.toFloat()
        llAddComment.translationY = 200F
        contentRoot.animate()
            .scaleY(1F)
            .setDuration(200)
            .setInterpolator(AccelerateInterpolator())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    animateContent()
                }
            })
            .start()
    }

    private fun animateContent() {
        //        commentsAdapter.updateItems();
        llAddComment.animate().translationY(0F)
            .setInterpolator(DecelerateInterpolator())
            .setDuration(200)
            .start()
    }
}
