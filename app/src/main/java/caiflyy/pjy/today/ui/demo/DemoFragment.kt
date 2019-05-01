package caiflyy.pjy.today.ui.demo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import caiflyy.pjy.today.R
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_list.*



/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.task
 * 日期：2019/4/25
 * 描述：示例项目列表信息界面
 * @author 蔡葳
 */
class DemoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val demoList = ArrayList<DemoItem>()
        demoList.add(
            DemoItem(
                R.drawable.shape_demo_item_ic_happy_birthday,
                R.string.demo_happy_birthday_title,
                R.string.demo_happy_birthday_desc
            )
        )
        demoList.add(
            DemoItem(
                R.drawable.shape_demo_item_ic_fun_story,
                R.string.demo_fun_story_title,
                R.string.demo_fun_story_desc
            )
        )
        demoList.add(
            DemoItem(
                R.drawable.shape_demo_item_ic_coffee_order,
                R.string.demo_coffee_order_title,
                R.string.demo_fun_story_desc
            )
        )
        demoList.add(
            DemoItem(
                R.drawable.shape_demo_item_ic_guess_number,
                R.string.demo_guess_number_title,
                R.string.demo_guess_number_desc
            )
        )
        val adapter = DemoListAdapter(R.layout.item_demo,demoList)
        rcv.layoutManager  = LinearLayoutManager(context)
        rcv.adapter = adapter
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            when(position){
                0->{}
                1->{}
                2->{}
            }
        }
    }
}
