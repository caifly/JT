package caiflyy.pjy.today.ui.demo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import caiflyy.pjy.today.R
import kotlinx.android.synthetic.main.fragment_demo09_add.*

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.demo
 * 日期：2019/5/6
 * 描述：第九个示例--记事簿添加事件界面
 * @author 蔡葳
 */
class Demo09FragmentAdd : Fragment() {
    private val task = Demo09Task.instance

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_demo09_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doneButton.setOnClickListener{
            val taskDescription = descriptionText.text.toString()
            if (taskDescription.isNotEmpty()) {
                task.tasks.add(taskDescription)
            }
            findNavController().popBackStack(R.id.demo09FragmentAdd,true)
        }
    }
}
