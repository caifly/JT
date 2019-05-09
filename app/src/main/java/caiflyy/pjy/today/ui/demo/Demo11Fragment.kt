package caiflyy.pjy.today.ui.demo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import caiflyy.pjy.today.R
import kotlinx.android.synthetic.main.fragment_demo11.*

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.demo
 * 日期：2019/5/8
 * 描述：第十一个示例--打招呼界面
 * @author 蔡葳
 */
class Demo11Fragment : Fragment() {
    private lateinit var viewModel: Demo11ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_demo11, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Demo11ViewModel::class.java)

        configureSpinner()
        configureRadioGroup()

        showButton.setOnClickListener {
            viewModel.updateGreeting(languageSpinner.selectedItem as Demo11Language) {
                configureGreeting()
            }
        }

        configureGreeting()
    }

    private fun configureSpinner() {
        languageSpinner.adapter = ArrayAdapter<Demo11Language>(context, android.R.layout.simple_spinner_dropdown_item, viewModel.languages)
        languageSpinner.setSelection(2)
    }

    private fun configureRadioGroup() {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val checkedRadioButton = group.findViewById(checkedId) as RadioButton
            viewModel.updateShowFormal(checkedRadioButton.text == getString(R.string.formal))
        }
    }

    private fun configureGreeting() {
        greeting.text = viewModel.greeting()
        count.text = viewModel.countText()
    }
}
