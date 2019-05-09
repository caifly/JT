package caiflyy.pjy.today.ui.demo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import caiflyy.pjy.today.R
import caiflyy.pjy.today.databinding.FragmentDemo10ExamPassBinding

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.demo
 * 日期：2019/5/8
 * 描述：第十个示例--测试题测试界面
 * @author 蔡葳
 */
class Demo10ExamPassFragment : Fragment() {

    private val questions: MutableList<Question> = mutableListOf(
        Question(text = "对于XML布局文件中的视图控件，layout_width属性的属性值不可以是什么？",
            answers = listOf("match_content", " wrap_content", "fill_parent", "match_parent")),
        Question(text = "下列关于Android布局文件常用的长度/大小单位的描述中，不正确的是？",
            answers = listOf("px是像素单位，在不同的设备上显示效果相同，因此推荐在布局中使用该单位", "dp是设备独立像素，不依赖于设备，是最常用的长度单位", "sp代表放大像素，主要用于字体大小的显示", "在设置空间长度等相对距离时，推荐使用dp单位，该单位随设备密度的变化而变化")),
        Question(text = "下列哪一个选项不属于Android中预定义的布局方式？",
            answers = listOf("TabLayout", "RelativeLayout", "LinearLayout", "FrameLayout")),
        Question(text = "系统收到intent发起的启动Activity的请求时，根据什么来选择最合适的Activity?",
            answers = listOf("都可以", "category", "data", "action")),
        Question(text = "Android中MVC模式 C层指的是？",
            answers = listOf("Activity", "Services", "Content", "Intents")),
        Question(text = "我们都知道Hanlder是线程与Activity通信的桥梁，如果线程处理不当，你的机器就会变得越慢，那么线程销毁的方法是?",
            answers = listOf("onDestroy()", "onClear()", "onFinish()", "onStop()")),
        Question(text = "Android清单文件的扩展名是什么？",
            answers = listOf(".xml", ".jar", ".java", ".apk")),
        Question(text = "在Android应用程序中,图片应放在那个目录下?",
            answers = listOf("drawable", "raw", "values", "layout")),
        Question(text = "Activity生命周期中,第一个需要执行的方法是什么?",
            answers = listOf("onCreate", "onStart", "onReStart", "onResume")),
        Question(text = "为实现布局文件的编译期数据绑定，布局文件的根标签应该使用那个?",
            answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>"))
    )

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 3)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentDemo10ExamPassBinding>(
            inflater, R.layout.fragment_demo10_exam_pass, container, false)
        randomizeQuestions()
        binding.exam = this
        binding.submitButton.setOnClickListener {
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++
                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    } else {
                        view?.findNavController()?.navigate(Demo10ExamPassFragmentDirections.actionDemo10Win(numQuestions,questionIndex))
                    }
                } else {
                    view?.findNavController()?.navigate(Demo10ExamPassFragmentDirections.actionDemo10Fail())
                }
            }
        }
        return binding.root
    }

    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        answers = currentQuestion.answers.toMutableList()
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions)
    }
}
