package caiflyy.pjy.today.ui.demo


import android.app.AlertDialog
import android.content.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import caiflyy.pjy.today.R
import kotlinx.android.synthetic.main.fragment_demo09_list.*
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.demo
 * 日期：2019/5/6
 * 描述：第九个示例--记事簿列表界面
 * @author 蔡葳
 */
class Demo09FragmentList : Fragment() {
    companion object {
        private fun getCurrentTimeStamp(): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
            val now = Date()
            return simpleDateFormat.format(now)
        }
    }

    private val sharedPreferences: SharedPreferences by inject()

    private val KEY_TASKS_LIST = "tasks_list"
    private val task = Demo09Task.instance

    private val adapter by lazy { makeAdapter() }
    private val tickReceiver by lazy { makeBroadcastReceiver() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (task.tasks.size==0){
            val savedList = sharedPreferences.getString(KEY_TASKS_LIST, null)
            if (savedList != null) {
                val items = savedList.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                task.tasks.addAll(items)
            }
        }
        return inflater.inflate(R.layout.fragment_demo09_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskListView.adapter = adapter
        taskListView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            taskSelected(position)
        }
        addTaskButton.setOnClickListener {
            findNavController().navigate(R.id.actionAddRemind)
        }
    }

    override fun onResume() {
        super.onResume()
        dateTimeTextView.text = getCurrentTimeStamp()
        context?.registerReceiver(tickReceiver, IntentFilter(Intent.ACTION_TIME_TICK))
        adapter.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        try {
            context?.unregisterReceiver(tickReceiver)
        } catch (e: IllegalArgumentException) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val savedList = StringBuilder()
        for (task in task.tasks) {
            savedList.append(task)
            savedList.append(",")
        }

        sharedPreferences.edit {
            putString(KEY_TASKS_LIST, savedList.toString())
        }
    }

    private fun makeAdapter(): ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_list_item_1, task.tasks)

    private fun makeBroadcastReceiver(): BroadcastReceiver {
        return object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent?) {
                if (intent?.action == Intent.ACTION_TIME_TICK) {
                    dateTimeTextView.text = getCurrentTimeStamp()
                }
            }
        }
    }

    private fun taskSelected(position: Int) {
        AlertDialog.Builder(context)
            .setTitle(R.string.alert_title)
            .setMessage(task.tasks[position])
            .setPositiveButton(R.string.delete) { _, _ ->
                task.tasks.removeAt(position)
                adapter.notifyDataSetChanged()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()
    }
}
