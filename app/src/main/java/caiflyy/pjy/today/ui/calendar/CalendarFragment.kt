package caiflyy.pjy.today.ui.calendar


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import caiflyy.pjy.today.R
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import kotlinx.android.synthetic.main.fragment_calendar.*

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.calendar
 * 日期：2019/4/25
 * 描述：日常任务信息界面
 * @author 蔡葳
 */
class CalendarFragment : Fragment(),
    CalendarView.OnDateSelectedListener,
    CalendarView.OnYearChangeListener,
    View.OnClickListener{

    private var mYear: Int = 0

    override fun onDateSelected(calendar: Calendar?, isClick: Boolean) {
        tv_lunar.visibility = View.VISIBLE
        tv_year.visibility = View.VISIBLE
        tv_month_day.text = calendar?.month.toString() + "月" + calendar?.day + "日"
        tv_year.text = calendar?.year.toString()
        tv_lunar.text = calendar?.lunar
        mYear = calendar?.year!!
    }

    override fun onYearChange(year: Int) {
        tv_month_day.text = year.toString()
    }

    override fun onClick(v: View?) {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_month_day.setOnClickListener{
            if (!calendarLayout.isExpand) {
                calendarView.showYearSelectLayout(mYear)
                return@setOnClickListener
            }
            calendarView.showYearSelectLayout(mYear)
            tv_lunar.visibility = View.GONE
            tv_year.visibility = View.GONE
            tv_month_day.text = mYear.toString()
        }
        fl_current.setOnClickListener {
            calendarView.scrollToCurrent()
        }
        calendarView.setOnDateSelectedListener(this)
        calendarView.setOnYearChangeListener(this)
        tv_year.text = calendarView.curYear.toString()
        mYear = calendarView.curYear
        tv_month_day.text = calendarView.curMonth.toString() + "月" + calendarView.curDay + "日";
        tv_lunar.text = "今日"
        tv_current_day.text = calendarView.curDay.toString()
        initData()
    }

    private fun initData() {
        val schemes = ArrayList<Calendar>()
        val year = calendarView.curYear
        val month = calendarView.curMonth
        schemes.add(getSchemeCalendar(year, month, 3, -0xbf24db, "假"))
        schemes.add(getSchemeCalendar(year, month, 6, -0x196ec8, "事"))
        schemes.add(getSchemeCalendar(year, month, 9, -0x20ecaa, "议"))
        schemes.add(getSchemeCalendar(year, month, 13, -0x123a93, "记"))
        schemes.add(getSchemeCalendar(year, month, 14, -0x123a93, "记"))
        schemes.add(getSchemeCalendar(year, month, 15, -0x5533bc, "假"))
        schemes.add(getSchemeCalendar(year, month, 18, -0x43ec10, "记"))
        schemes.add(getSchemeCalendar(year, month, 25, -0xec5310, "假"))
        schemes.add(getSchemeCalendar(year, month, 27, -0xec5310, "多"))
        calendarView.setSchemeDate(schemes)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun getSchemeCalendar(year: Int, month: Int, day: Int, color: Int, text: String): Calendar {
        val calendar = Calendar()
        calendar.year = year
        calendar.month = month
        calendar.day = day
        //如果单独标记颜色、则会使用这个颜色
        calendar.schemeColor = color
        calendar.scheme = text
        calendar.addScheme(Calendar.Scheme())
        calendar.addScheme(-0xff7800, "假")
        calendar.addScheme(-0xff7800, "节")
        return calendar
    }
}
