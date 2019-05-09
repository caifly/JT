package caiflyy.pjy.today.ui.demo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import caiflyy.pjy.today.R

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.ui.demo
 * 日期：2019-05-08
 * 描述：
 * @author 蔡葳
 */
class Demo11ViewModel(application: Application) : AndroidViewModel(application) {

    var greetingCount = 0
        private set

    var showFormal = true
        private set

    var language = Demo11GreetingStore.defaultLanguage
        private set

    val languages: List<Demo11Language> by lazy {
        Demo11GreetingStore.languages
    }

    fun updateGreeting(language: Demo11Language, callback: () -> Unit) {
        this.language = language
        greetingCount++
        callback()
    }

    fun updateShowFormal(showFormal: Boolean) {
        this.showFormal = showFormal
    }

    fun greeting() = if (showFormal) language.greeting.formal else language.greeting.informal

    fun countText(): String {
        return getApplication<Application>().resources.getQuantityString(R.plurals.greetings, greetingCount, greetingCount)
    }
}