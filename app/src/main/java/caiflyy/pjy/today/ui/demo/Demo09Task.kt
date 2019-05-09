package caiflyy.pjy.today.ui.demo


/**
 * 项目名称：S0402
 * 包名：caiflyy.pjy.s0402
 * 日期：2019/1/24
 * 描述：
 * @author 蔡葳
 */
class Demo09Task() {
    private object Holder { val INSTANCE = Demo09Task() }

    companion object {
        val instance: Demo09Task by lazy { Holder.INSTANCE }
    }
    val tasks= mutableListOf<String>()

    fun addTask(task:String) {
        tasks.add(task)
    }
}