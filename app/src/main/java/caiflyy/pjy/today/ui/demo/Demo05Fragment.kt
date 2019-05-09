package caiflyy.pjy.today.ui.demo


import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import caiflyy.pjy.today.BuildConfig
import caiflyy.pjy.today.R
import caiflyy.pjy.today.utils.TAG
import kotlinx.android.synthetic.main.fragment_demo05.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.support.v4.toast

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.demo
 * 日期：2019/5/6
 * 描述：综合练习--点击游戏
 * @author 蔡葳
 */
class Demo05Fragment : Fragment() {
    private var score = 0
    private var gameStarted = false

    private lateinit var countDownTimer : CountDownTimer
    private val initialCountDown:Long = 30000
    private val countDownInterval: Long = 1000
    private var timeLeftOnTimer: Long = 30000

    companion object {
        private val SCORE_KEY = "SCORE_KEY"
        private val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_demo05, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        btnClick.setOnClickListener {
            val bounceAnimation = AnimationUtils.loadAnimation(context,
                R.anim.bounce);
            it.startAnimation(bounceAnimation)
            incrementScore()
        }
        if (savedInstanceState != null){
            score = savedInstanceState.getInt(SCORE_KEY)
            timeLeftOnTimer = savedInstanceState.getLong(TIME_LEFT_KEY)
            restoreGame()
        }else{
            resetGame()
        }
    }

    private fun restoreGame() {
        tvScore.text = getString(R.string.tvScore,score.toString())
        val restoredTime = timeLeftOnTimer / 1000
        tvTime.text = getString(R.string.tvTime,restoredTime.toString())
        countDownTimer = object :CountDownTimer(timeLeftOnTimer,countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished
                val timeLeft = millisUntilFinished / 1000
                tvTime.text = getString(R.string.tvTime,timeLeft.toString())
            }

            override fun onFinish() {
                endGame()
            }
        }
        countDownTimer.start()
        gameStarted = true
    }

    private fun incrementScore() {
        if (!gameStarted) {
            startGame()
        }
        score += 1
        tvScore.text = getString(R.string.tvScore, score.toString())
    }

    private fun resetGame() {
        score = 0
        tvScore.text = getString(R.string.tvScore,score.toString())
        val initialTimeLeft = initialCountDown / 1000
        tvTime.text = getString(R.string.tvTime,initialTimeLeft.toString())
        countDownTimer = object : CountDownTimer(initialCountDown,countDownInterval){
            override fun onFinish() {
                endGame()
            }

            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished
                val timeLeft = millisUntilFinished / 1000
                tvTime.text = getString(R.string.tvTime, timeLeft.toString())
            }
        }
        gameStarted = false
    }

    private fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(TIME_LEFT_KEY,timeLeftOnTimer)
        outState.putInt(SCORE_KEY,score)
        countDownTimer.cancel()
    }

    private fun endGame() {
        toast(getString(R.string.game_over_message,score.toString()))
        resetGame()
    }

    override fun onStart() {
        super.onStart()
        AnkoLogger(TAG).error("onStart called.")
    }

    override fun onResume() {
        super.onResume()
        AnkoLogger(TAG).error("onResume called.")
    }

    override fun onPause() {
        super.onPause()
        countDownTimer.cancel()
        AnkoLogger(TAG).error("onPause called.")
    }

    override fun onStop() {
        super.onStop()
        AnkoLogger(TAG).error("onStop called.")
    }

    override fun onDestroy() {
        super.onDestroy()
        AnkoLogger(TAG).error("onDestroy called.")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.game_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_about){
            val dialogTitle = getString(R.string.about_title, BuildConfig.VERSION_NAME)
            val dialogMessage = getString(R.string.about_message)
            val builder = AlertDialog.Builder(context)
            builder.setTitle(dialogTitle)
            builder.setMessage(dialogMessage)
            builder.create().show()
        }
        return true
    }

}
