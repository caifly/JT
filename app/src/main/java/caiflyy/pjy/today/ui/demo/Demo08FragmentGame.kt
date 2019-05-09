package caiflyy.pjy.today.ui.demo


import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import caiflyy.pjy.today.R
import kotlinx.android.synthetic.main.fragment_demo08_game.*

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.demo
 * 日期：2019/5/6
 * 描述：第八个示例--猜数字游戏界面
 * @author 蔡葳
 */
class Demo08FragmentGame : Fragment() {
    private lateinit var countDownTimer : CountDownTimer
    private val countDownInterval: Long = 1000
    private var timeLeftOnTimer: Long = 30000
    private val game = Game.instance

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_demo08_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countDownTimer = object : CountDownTimer(timeLeftOnTimer, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished
                val timeLeft = millisUntilFinished / 1000
                tvTime.text = timeLeft.toString()
            }

            override fun onFinish() {
                countDownTimer.cancel()
                game.result=Game.FAIL
                findNavController().popBackStack(R.id.demo08FragmentGame,true)
            }
        }
        countDownTimer.start()
        tvResult.text = game.title
        btnGuess.setOnClickListener {
            val inputNumber = edtInput.text.toString().trim().toInt()
            val result = game.compare(inputNumber)
            if (result == "win") {
                game.result=Game.WIN
                findNavController().navigate(R.id.actionGameOver)
            } else {
                tvResult.text = result
                edtInput.setText("")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }
}
