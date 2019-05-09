package caiflyy.pjy.today.ui.demo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import caiflyy.pjy.today.R
import kotlinx.android.synthetic.main.fragment_demo06.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.textColor

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.demo
 * 日期：2019/5/6
 * 描述：第六个示例--随机趣事
 * @author 蔡葳
 */
class Demo06Fragment : Fragment() {
    private val book = FunBook()
    private val colorWheel = ColorWheel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_demo06, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeUI()
        button.setOnClickListener{
            changeUI()
        }
    }

    private fun changeUI() {
        val story = book.getStory()
        val color = colorWheel.getColor()
        tv_title.text = story.title
        tv_body.text = story.body
        layout.backgroundColor = color
        button.textColor = color
    }
}
