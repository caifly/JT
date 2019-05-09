package caiflyy.pjy.today.ui.demo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import caiflyy.pjy.today.R
import caiflyy.pjy.today.utils.TAG
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.demo
 * 日期：2019/4/25
 * 描述：第一个示例--生日贺卡界面
 * @author 蔡葳
 */
class Demo01Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_demo01, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        AnkoLogger(TAG).error("-----------")
        return true
    }
}
