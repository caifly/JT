package caiflyy.pjy.today.ui.demo


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import caiflyy.pjy.today.R
import kotlinx.android.synthetic.main.fragment_demo12.*
import org.jetbrains.anko.windowManager
import java.util.*

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.demo
 * 日期：2019-05-08
 * 描述：第十二个示例--满天星界面
 * @author 蔡葳
 */
class Demo12Fragment : Fragment() {
    private lateinit var starViewModel: Demo12ViewModel

    private val starViews = mutableListOf<View>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_demo12, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        starViewModel = ViewModelProviders.of(this).get(Demo12ViewModel::class.java)

        setupButtons()

        starViewModel.starLiveData.observe(this, Observer { star ->
            animateStar(star)
        })

        starViewModel.emittingLiveData.observe(this, Observer { emitting ->
            resetButton.isEnabled = emitting ?: false
            startButton.isEnabled = !resetButton.isEnabled
        })
    }

    private fun setupButtons() {
        startButton.setOnClickListener {
            starViewModel.startEmittingStars()
        }

        resetButton.setOnClickListener {
            starViewModel.stopEmittingStars()
            starViews.forEach { starField.removeView(it) }
            starViews.clear()
        }
    }

    override fun onResume() {
        super.onResume()
        val displayMetrics = DisplayMetrics()
        context?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        starViewModel.setupDisplay(displayMetrics.widthPixels.toDouble(), displayMetrics.heightPixels.toDouble())
    }

    private fun animateStar(star: Demo12Star?) {
        if (star != null) {
            val starView = createStarView(star)

            val xAnimator = objectAnimatorOfFloat(starView, "x", star.x.toFloat(), star.endX.toFloat())
            val yAnimator = objectAnimatorOfFloat(starView, "y", star.y.toFloat(), star.endY.toFloat())

            starField.addView(starView)
            starViews.add(starView)

            AnimatorSet().apply {
                play(xAnimator).with(yAnimator)
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        starField.removeView(starView)
                    }
                })
                start()
            }
        }
    }

    private fun createStarView(star: Demo12Star): View {
        val starView = View(context)
        val starSize = Random().nextInt((MAX_SIZE-MIN_SIZE)+MIN_SIZE )
        starView.layoutParams = FrameLayout.LayoutParams(starSize, starSize)
        starView.x = star.x.toFloat()
        starView.y = star.y.toFloat()
        starView.setBackgroundColor(Color.parseColor(STAR_COLOR))
        return starView
    }

    private fun objectAnimatorOfFloat(view: View, propertyName: String, startValue: Float, endValue: Float): ObjectAnimator {
        val animator = ObjectAnimator.ofFloat(view, propertyName, startValue, endValue)
        animator.interpolator = LinearInterpolator()
        animator.duration = DURATION
        return animator
    }

    companion object {
        private const val DURATION = 6000L
        private const val STAR_COLOR = "#ffffff"
        private const val MIN_SIZE = 1
        private const val MAX_SIZE = 8
    }

}
