package caiflyy.pjy.today.ui.demo


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import caiflyy.pjy.today.R
import caiflyy.pjy.today.databinding.FragmentDemo03Binding

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.demo
 * 日期：2019/5/6
 * 描述：第三个示例--自我介绍
 * @author 蔡葳
 */
class Demo03Fragment : Fragment() {

    private val me = Me(
        name = "蔡葳",
        icon = R.drawable.img_demo03_icon,
        desc = """
            大家好，我叫蔡葳

            我是一名老程序员

            我毕业于深圳大学计算机系，从事开发工作20多年，从事教育工作8年多，精通java，python，kotlin等计算机语言。我是北京人，喜好美食和旅游。
            现在是互联网时代，人们当下的工作基本是互联网加自己的业务。
            未来将是人工智能的时代，人们的工作会变成人工智能加自己的业务。

            不管一个技术有多高大上，它最终的目的都是要帮助人们更好的工作和生活，技术必须要落地才有价值，而移动端将是最普遍的技术落地方式。
        """.trimIndent()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDemo03Binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_demo03, container, false
        )
        binding.me = me
        binding.btn.setOnClickListener {
            binding.apply {
                me?.school = edt.text.toString()
                invalidateAll()
                tvSchool.visibility = View.VISIBLE
                edt.visibility = View.GONE
                btn.visibility = View.GONE
            }

            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
        return binding.root
    }
}
