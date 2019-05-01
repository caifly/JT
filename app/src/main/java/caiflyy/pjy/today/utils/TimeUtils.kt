package caiflyy.pjy.today.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.utils
 * 日期：2019/4/15
 * 描述：时间操作函数
 * @author 蔡葳
 */
/**
 * 毫秒与毫秒的倍数
 */
val MSEC = 1
/**
 * 秒与毫秒的倍数
 */
val SEC = 1000
/**
 * 分与毫秒的倍数
 */
val MIN = 60000
/**
 * 时与毫秒的倍数
 */
val HOUR = 3600000
/**
 * 天与毫秒的倍数
 */
val DAY = 86400000

val DEFAULT_SDF = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

val UTC_SDF = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

val DATE_SDF = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

val DATE_NO_YEAR_SDF = SimpleDateFormat("MM-dd", Locale.getDefault())

val HOUR_SDF = SimpleDateFormat("HH:mm", Locale.getDefault())

val HOURLY_FORECAST_SDF = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

/**
 * 将时间戳转为时间字符串
 *
 * 格式为yyyy-MM-dd HH:mm:ss
 *
 * @param milliseconds 毫秒时间戳
 * @return 时间字符串
 */
fun milliseconds2String(milliseconds: Long): String {
    return milliseconds2String(milliseconds, DEFAULT_SDF)
}

/**
 * 将时间戳转为时间字符串
 *
 * 格式为用户自定义
 *
 * @param milliseconds 毫秒时间戳
 * @param format       时间格式
 * @return 时间字符串
 */
fun milliseconds2String(milliseconds: Long, format: SimpleDateFormat): String {
    return format.format(Date(milliseconds))
}

/**
 * 将时间字符串转为时间戳
 *
 * 格式为yyyy-MM-dd HH:mm:ss
 *
 * @param time 时间字符串
 * @return 毫秒时间戳
 */
fun string2Milliseconds(time: String): Long {
    return string2Milliseconds(time, DEFAULT_SDF)
}

/**
 * 将时间字符串转为时间戳
 *
 * 格式为用户自定义
 *
 * @param time   时间字符串
 * @param format 时间格式
 * @return 毫秒时间戳
 */
fun string2Milliseconds(time: String, format: SimpleDateFormat): Long {
    try {
        return format.parse(time).getTime()
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return -1
}

/**
 * 将时间字符串转为Date类型
 *
 * 格式为yyyy-MM-dd HH:mm:ss
 *
 * @param time 时间字符串
 * @return Date类型
 */
fun string2Date(time: String): Date {
    return string2Date(time, DEFAULT_SDF)
}

/**
 * 将时间字符串转为Date类型
 *
 * 格式为用户自定义
 *
 * @param time   时间字符串
 * @param format 时间格式
 * @return Date类型
 */
fun string2Date(time: String, format: SimpleDateFormat): Date {
    return Date(string2Milliseconds(time, format))
}

/**
 * 将Date类型转为时间字符串
 *
 * 格式为yyyy-MM-dd HH:mm:ss
 *
 * @param time Date类型时间
 * @return 时间字符串
 */
fun date2String(time: Date): String {
    return date2String(time, DEFAULT_SDF)
}

/**
 * 将Date类型转为时间字符串
 *
 * 格式为用户自定义
 *
 * @param time   Date类型时间
 * @param format 时间格式
 * @return 时间字符串
 */
fun date2String(time: Date, format: SimpleDateFormat): String {
    return format.format(time)
}

/**
 * 将Date类型转为时间戳
 *
 * @param time Date类型时间
 * @return 毫秒时间戳
 */
fun date2Milliseconds(time: Date): Long {
    return time.getTime()
}

/**
 * 将时间戳转为Date类型
 *
 * @param milliseconds 毫秒时间戳
 * @return Date类型时间
 */
fun milliseconds2Date(milliseconds: Long): Date {
    return Date(milliseconds)
}

/**
 * 毫秒时间戳单位转换（单位：unit）
 *
 * @param milliseconds 毫秒时间戳
 * @param unit
 * @return unit时间戳
 */
private fun milliseconds2Unit(milliseconds: Long, unit: Int): Long {
    when (unit) {
        MSEC -> return milliseconds / MSEC
        SEC -> return milliseconds / SEC
        MIN -> return milliseconds / MIN
        HOUR -> return milliseconds / HOUR
        DAY -> return milliseconds / DAY
    }
    return -1
}

/**
 * 获取两个时间差（单位：unit）
 *
 * time1和time2格式都为yyyy-MM-dd HH:mm:ss
 *
 * @param time0 时间字符串1
 * @param time1 时间字符串2
 * @param unit
 * @return unit时间戳
 */
fun getIntervalTime(time0: String, time1: String, unit: Int): Long {
    return getIntervalTime(time0, time1, unit, DEFAULT_SDF)
}

/**
 * 获取两个时间差（单位：unit）
 *
 * time1和time2格式都为format
 *
 * @param time0  时间字符串1
 * @param time1  时间字符串2
 * @param unit
 * @param format 时间格式
 * @return unit时间戳
 */
fun getIntervalTime(time0: String, time1: String, unit: Int, format: SimpleDateFormat): Long {
    return milliseconds2Unit(Math.abs(string2Milliseconds(time0, format) - string2Milliseconds(time1, format)), unit)
}

/**
 * 获取两个时间差（单位：unit）
 *
 * time1和time2都为Date类型
 *
 * @param time0 Date类型时间1
 * @param time1 Date类型时间2
 * @param unit
 * @return unit时间戳
 */
fun getIntervalTime(time0: Date, time1: Date, unit: Int): Long {
    return milliseconds2Unit(Math.abs(date2Milliseconds(time1) - date2Milliseconds(time0)), unit)
}

/**
 * 获取当前时间
 *
 * @return 毫秒时间戳
 */
fun getCurTimeMills(): Long {
    return System.currentTimeMillis()
}

/**
 * 获取当前时间
 *
 * 格式为yyyy-MM-dd HH:mm:ss
 *
 * @return 时间字符串
 */
fun getCurTimeString(): String {
    return date2String(Date())
}

/**
 * 获取当前时间
 *
 * 格式为用户自定义
 *
 * @param format 时间格式
 * @return 时间字符串
 */
fun getCurTimeString(format: SimpleDateFormat): String {
    return date2String(Date(), format)
}

/**
 * 获取当前时间
 *
 * Date类型
 *
 * @return Date类型时间
 */
fun getCurTimeDate(): Date {
    return Date()
}

/**
 * 获取与当前时间的差（单位：unit）
 *
 * time格式为yyyy-MM-dd HH:mm:ss
 *
 * @param time 时间字符串
 * @param unit
 * @return unit时间戳
 */
fun getIntervalByNow(time: String, unit: Int): Long {
    return getIntervalByNow(time, unit, DEFAULT_SDF)
}

/**
 * 获取与当前时间的差（单位：unit）
 *
 * time格式为format
 *
 * @param time   时间字符串
 * @param unit
 * @param format 时间格式
 * @return unit时间戳
 */
fun getIntervalByNow(time: String, unit: Int, format: SimpleDateFormat): Long {
    return getIntervalTime(getCurTimeString(), time, unit, format)
}

/**
 * 获取与当前时间的差（单位：unit）
 *
 * time为Date类型
 *
 * @param time Date类型时间
 * @param unit
 * @return unit时间戳
 */
fun getIntervalByNow(time: Date, unit: Int): Long {
    return getIntervalTime(getCurTimeDate(), time, unit)
}

/**
 * 判断闰年
 *
 * @param year 年份
 * @return `true`: 闰年<br></br>`false`: 平年
 */
fun isLeapYear(year: Int): Boolean {
    return year % 4 == 0 && year % 100 != 0 || year % 400 == 0
}

/**
 * 获取星期
 *
 * time格式为yyyy-MM-dd HH:mm:ss
 *
 * @param time 时间字符串
 * @return 星期
 */
fun getWeek(time: String): String {
    return SimpleDateFormat("EEEE", Locale.getDefault()).format(string2Date(time))
}

/**
 * 获取星期
 *
 * @param time   时间字符串
 * @param format 时间格式
 * @return 星期
 */
fun getWeek(time: String, format: SimpleDateFormat): String {
    return SimpleDateFormat("EEEE", Locale.getDefault()).format(string2Date(time, format))
}

/**
 * 获取星期
 *
 * @param time Date类型时间
 * @return 星期
 */
fun getWeek(time: Date): String {
    return SimpleDateFormat("EEEE", Locale.getDefault()).format(time)
}

/**
 * 获取星期
 *
 * 注意：周日的Index才是1，周六为7
 *
 * time格式为yyyy-MM-dd HH:mm:ss
 *
 * @param time 时间字符串
 * @return 1...5
 */
fun getWeekIndex(time: String): Int {
    val date = string2Date(time)
    return getWeekIndex(date)
}

/**
 * 获取星期
 *
 * 注意：周日的Index才是1，周六为7
 *
 * @param time   时间字符串
 * @param format 时间格式
 * @return 1...7
 */
fun getWeekIndex(time: String, format: SimpleDateFormat): Int {
    val date = string2Date(time, format)
    return getWeekIndex(date)
}

/**
 * 获取星期
 *
 * 注意：周日的Index才是1，周六为7
 *
 * @param time Date类型时间
 * @return 1...7
 */
fun getWeekIndex(time: Date): Int {
    val cal = Calendar.getInstance()
    cal.setTime(time)
    return cal.get(Calendar.DAY_OF_WEEK)
}

/**
 * 获取月份中的第几周
 *
 * 注意：国外周日才是新的一周的开始
 *
 * time格式为yyyy-MM-dd HH:mm:ss
 *
 * @param time 时间字符串
 * @return 1...5
 */
fun getWeekOfMonth(time: String): Int {
    val date = string2Date(time)
    return getWeekOfMonth(date)
}

/**
 * 获取月份中的第几周
 *
 * 注意：国外周日才是新的一周的开始
 *
 * @param time   时间字符串
 * @param format 时间格式
 * @return 1...5
 */
fun getWeekOfMonth(time: String, format: SimpleDateFormat): Int {
    val date = string2Date(time, format)
    return getWeekOfMonth(date)
}

/**
 * 获取月份中的第几周
 *
 * 注意：国外周日才是新的一周的开始
 *
 * @param time Date类型时间
 * @return 1...5
 */
fun getWeekOfMonth(time: Date): Int {
    val cal = Calendar.getInstance()
    cal.setTime(time)
    return cal.get(Calendar.WEEK_OF_MONTH)
}

/**
 * 获取年份中的第几周
 *
 * 注意：国外周日才是新的一周的开始
 *
 * time格式为yyyy-MM-dd HH:mm:ss
 *
 * @param time 时间字符串
 * @return 1...54
 */
fun getWeekOfYear(time: String): Int {
    val date = string2Date(time)
    return getWeekOfYear(date)
}

/**
 * 获取年份中的第几周
 *
 * 注意：国外周日才是新的一周的开始
 *
 * @param time   时间字符串
 * @param format 时间格式
 * @return 1...54
 */
fun getWeekOfYear(time: String, format: SimpleDateFormat): Int {
    val date = string2Date(time, format)
    return getWeekOfYear(date)
}

/**
 * 获取年份中的第几周
 *
 * 注意：国外周日才是新的一周的开始
 *
 * @param time Date类型时间
 * @return 1...54
 */
fun getWeekOfYear(time: Date): Int {
    val cal = Calendar.getInstance()
    cal.setTime(time)
    return cal.get(Calendar.WEEK_OF_YEAR)
}

fun string2String(time: String, fromFormat: SimpleDateFormat, toFormat: SimpleDateFormat): String {
    return date2String(string2Date(time, fromFormat), toFormat)
}

fun isBefore(d1: Date, d2: Date): Boolean {
    return d1.before(d2)
}

fun isBefore(d1: String, d2: String, sdf: SimpleDateFormat): Boolean {
    try {
        return sdf.parse(d1).before(sdf.parse(d2))
    } catch (e: Exception) {
        return false
    }

}

/**
 * 获取某个日期的前几天的日期
 *
 * @param dateString 某日期
 * @param dayNumber  前面第几天
 * @return
 */
fun getPreviousDay(dateString: String, dayNumber: Int): String {
    val c = Calendar.getInstance()
    var date: Date? = null
    try {
        date = SimpleDateFormat("yyyy-MM-dd").parse(dateString)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    c.setTime(date)
    val day = c.get(Calendar.DATE)
    c.set(Calendar.DATE, day - dayNumber)

    return SimpleDateFormat("yyyy-MM-dd").format(c.getTime())
}

fun getSystemTime(): String {
    return System.currentTimeMillis().toString()
}

/**
 * 判断当前时间是否在两个时间段之内
 *
 * @param beginTime 开始时间
 * @param endTime   结束时间
 * @return
 */
fun isNowBetween(beginTime: String, endTime: String): Boolean {
    val df = SimpleDateFormat("HH:mm")
    var now: Date? = null
    var begin: Date? = null
    var end: Date? = null
    try {
        now = df.parse(df.format(Date()))
        begin = df.parse(beginTime)
        end = df.parse(endTime)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    val nowCal = Calendar.getInstance()
    nowCal.setTime(now)

    val beginCal = Calendar.getInstance()
    beginCal.setTime(begin)

    val endCal = Calendar.getInstance()
    endCal.setTime(end)

    return if (nowCal.after(beginCal) && nowCal.before(endCal)) {
        true
    } else {
        false
    }
}

/**
 * 获取当前时间在某段时间内的百分比位置
 *
 * @param beginTime 开始时间
 * @param endTime   结束时间
 * @return
 */
fun getTimeDiffPercent(beginTime: String, endTime: String): Float {
    val df = SimpleDateFormat("HH:mm")
    var now: Date? = null
    var begin: Date? = null
    var end: Date? = null
    try {
        now = df.parse(df.format(Date()))
        begin = df.parse(beginTime)
        end = df.parse(endTime)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return (now!!.time - begin!!.time).toFloat() / (end!!.time - begin!!.time).toFloat()
}
