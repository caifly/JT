package caiflyy.pjy.today.ui.demo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import caiflyy.pjy.today.R
import kotlinx.android.synthetic.main.fragment_demo08_menu.*
import org.jetbrains.anko.imageResource

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.demo
 * 日期：2019/5/6
 * 描述：第八个示例--猜数字菜单界面
 * @author 蔡葳
 */
class Demo08FragmentMenu : Fragment() {
    private val game = Game.instance

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_demo08_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listener = ButtonListener()
        btnNormal.setOnClickListener(listener)
        btnHard.setOnClickListener(listener)
    }

    override fun onResume() {
        super.onResume()
        if (game.gameLevel == Game.NORMAL_LEVEL) {
            if (game.result == Game.WIN) {
                img.imageResource = R.drawable.img_demo08_normal_win
            } else {
                img.imageResource = R.drawable.img_demo08_normal_fail
            }
        }
        if(game.gameLevel == Game.HARD_LEVEL) {
            if (game.result == Game.WIN) {
                img.imageResource = R.drawable.img_demo08_hard_win
            } else {
                img.imageResource = R.drawable.img_demo08_hard_fail
            }
        }
    }

    inner class ButtonListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val bundle = Bundle()
            when (v?.id) {
                R.id.btnNormal -> {
                    game.start(Game.NORMAL_LEVEL)
                }
                R.id.btnHard -> {
                    game.start(Game.HARD_LEVEL)
                }
            }
            findNavController().navigate(R.id.actionGuessNumber, bundle)
        }
    }
}
