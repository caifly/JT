package caiflyy.pjy.today.ui.demo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.transition.TransitionManager
import caiflyy.pjy.today.R
import caiflyy.pjy.today.databinding.FragmentDemo07OrderBinding
import kotlinx.android.synthetic.main.fragment_demo07_order.*
import org.jetbrains.anko.support.v4.toast

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.demo
 * 日期：2019/5/6
 * 描述：第七个示例--咖啡订单
 * @author 蔡葳
 */
class Demo07Fragment : Fragment() {

    private val orderView: ConstraintSet =ConstraintSet()
    private val okView:ConstraintSet= ConstraintSet()
    private var isOrder:Boolean=false
    private lateinit var binding: FragmentDemo07OrderBinding
    private val order:CoffeeOrder = CoffeeOrder()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_demo07_order, container, false)
        binding.order = order
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderView.clone(constraintLayout)
        okView.clone(context,R.layout.fragment_demo07_confrim)
        btnOrder.setOnClickListener {
            TransitionManager.beginDelayedTransition(constraintLayout)
            isOrder = if (isOrder){
                orderView.applyTo(constraintLayout)
                tvInfo.text=order.title
                false
            }else{
                showOrderResult()
                okView.applyTo(constraintLayout)
                true
            }
        }
        btnAdd.setOnClickListener {
            addCoffee()
        }
        btnSub.setOnClickListener {
            subCoffee()
        }
        val listener = CheckBoxListener()
        ck01.setOnCheckedChangeListener(listener)
        ck02.setOnCheckedChangeListener(listener)
        ck03.setOnCheckedChangeListener(listener)
    }

    private fun subCoffee() {
        if (order.count.toInt() == 1) {
            toast("至少要订购${order.count}杯咖啡。")
        } else {
            order.count = (order.count.toInt() - 1).toString()
            binding.tvCount.text = order.count
        }
    }

    private fun addCoffee() {
        if (order.count.toInt() == 10) {
            toast("最多只能订购${order.count}杯咖啡。")
        } else {
            order.count = (order.count.toInt() + 1).toString()
            binding.tvCount.text = order.count
        }
    }

    private fun showOrderResult() {
        val builder: StringBuilder = java.lang.StringBuilder()
        builder.append("您订购了${order.count}杯咖啡\n")
        var count = 0
        for (dessert in order.dessert) {
            if (dessert) {
                count++
            }
        }
        if (count != 0) {
            builder.append("您订购了${count}份甜品\n")
        }
        builder.append("总计：${order.count.toInt() * 20 + count * 10}元")
        tvInfo.text = builder.toString()
    }

    inner class CheckBoxListener: CompoundButton.OnCheckedChangeListener{
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            when(buttonView?.id){
                R.id.ck01 ->order.dessert[0]=isChecked
                R.id.ck02 ->order.dessert[1]=isChecked
                R.id.ck03 ->order.dessert[2]=isChecked
            }
        }
    }
}
