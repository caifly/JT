package caiflyy.pjy.today.ui.demo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import caiflyy.pjy.today.R
import kotlinx.android.synthetic.main.fragment_demo10_exam_win.*

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.demo
 * 日期：2019/5/8
 * 描述：第十个示例--测试题胜利界面
 * @author 蔡葳
 */
class Demo10ExamWinFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_demo10_exam_win, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextMatchButton.setOnClickListener { _: View ->
            view.findNavController().navigate(Demo10ExamWinFragmentDirections.actionWinOver())
        }
    }
}
