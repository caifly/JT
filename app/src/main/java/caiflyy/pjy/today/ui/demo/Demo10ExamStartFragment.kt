package caiflyy.pjy.today.ui.demo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import caiflyy.pjy.today.R
import kotlinx.android.synthetic.main.fragment_demo10_exam_start.*

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.demo
 * 日期：2019/5/8
 * 描述：第十个示例--测试题开始界面
 * @author 蔡葳
 */
class Demo10ExamStartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_demo10_exam_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playButton.setOnClickListener {
            it.findNavController().navigate(Demo10ExamStartFragmentDirections.actionDemo10Pass())
        }
    }


}
