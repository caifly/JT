package caiflyy.pjy.today.ui.demo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import caiflyy.pjy.today.R
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_list.*


/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.demo
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
        demoList.apply {
            add(
                DemoItem(
                    R.drawable.shape_demo_item_ic_happy_birthday,
                    R.string.demo_happy_birthday_title,
                    R.string.demo_happy_birthday_desc
                )
            )
            add(
                DemoItem(
                    R.drawable.shape_demo_item_ic_dice,
                    R.string.demo_happy_dice_title,
                    R.string.demo_happy_dice_desc
                )
            )
            add(
                DemoItem(
                    R.drawable.shape_demo_item_ic_me,
                    R.string.demo_happy_me_title,
                    R.string.demo_happy_me_desc
                )
            )
            add(
                DemoItem(
                    R.drawable.shape_demo_item_ic_color,
                    R.string.demo_happy_color_title,
                    R.string.demo_happy_color_desc
                )
            )
            add(
                DemoItem(
                    R.drawable.shape_demo_item_ic_click,
                    R.string.demo_happy_click_title,
                    R.string.demo_happy_click_desc
                )
            )
            add(
                DemoItem(
                    R.drawable.shape_demo_item_ic_fun_story,
                    R.string.demo_fun_story_title,
                    R.string.demo_fun_story_desc
                )
            )
            add(
                DemoItem(
                    R.drawable.shape_demo_item_ic_coffee_order,
                    R.string.demo_coffee_order_title,
                    R.string.demo_fun_story_desc
                )
            )
            add(
                DemoItem(
                    R.drawable.shape_demo_item_ic_guess_number,
                    R.string.demo_guess_number_title,
                    R.string.demo_guess_number_desc
                )
            )
            add(
                DemoItem(
                    R.drawable.shape_demo_item_ic_remind,
                    R.string.demo_remind_title,
                    R.string.demo_remind_desc
                )
            )
            add(
                DemoItem(
                    R.drawable.shape_demo_item_ic_exam,
                    R.string.demo_exam_title,
                    R.string.demo_exam_desc
                )
            )
            add(
                DemoItem(
                    R.drawable.shape_demo_item_ic_greeting,
                    R.string.demo_greeting_title,
                    R.string.demo_greeting_desc
                )
            )
            add(
                DemoItem(
                    R.drawable.shape_demo_item_ic_star,
                    R.string.demo_star_title,
                    R.string.demo_star_desc
                )
            )
        }

        val adapter = DemoListAdapter(R.layout.item_demo, demoList)
        rcv.layoutManager = LinearLayoutManager(context)
        rcv.adapter = adapter
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener(function = { _, _, position ->
            when (position) {
                0 -> findNavController().navigate(R.id.actionDemo01)
                1 -> findNavController().navigate(R.id.actionDemo02)
                2 -> findNavController().navigate(R.id.actionDemo03)
                3 -> findNavController().navigate(R.id.actionDemo04)
                4 -> findNavController().navigate(R.id.actionDemo05)
                5 -> findNavController().navigate(R.id.actionDemo06)
                6 -> findNavController().navigate(R.id.actionDemo07)
                7 -> findNavController().navigate(R.id.actionDemo08)
                8 -> findNavController().navigate(R.id.actionDemo09)
                9 -> findNavController().navigate(R.id.actionDemo10)
                10 -> findNavController().navigate(R.id.actionDemo11)
                11 -> findNavController().navigate(R.id.actionDemo12)
            }
        })
    }
}
