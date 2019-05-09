package caiflyy.pjy.today.ui.demo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import caiflyy.pjy.today.R
import kotlinx.android.synthetic.main.fragment_demo02.*
import java.util.*

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.demo
 * 日期：2019/4/25
 * 描述：第二个示例--掷骰子游戏
 * @author 蔡葳
 */
class Demo02Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_demo02, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn.setOnClickListener{
            val number = Random().nextInt(6)
            when(number){
                0->img.setImageResource(R.drawable.dice_1)
                1->img.setImageResource(R.drawable.dice_2)
                2->img.setImageResource(R.drawable.dice_3)
                3->img.setImageResource(R.drawable.dice_4)
                4->img.setImageResource(R.drawable.dice_5)
                5->img.setImageResource(R.drawable.dice_6)
            }
            tv.text = (number+1).toString()
        }
    }


}
