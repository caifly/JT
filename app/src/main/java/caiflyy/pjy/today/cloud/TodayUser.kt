package caiflyy.pjy.today.data

import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobGeoPoint

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.data
 * 日期：2019/4/12
 * 描述：用户实体类
 * @author 蔡葳
 */
data class TodayUser(
    val title: String="无地址",
    var location: BmobGeoPoint?=null,
    var headIcon:String="没有设置",
    var description:String="没有设置",
    var nickname:String="没有设置",
    var realName:String="没有设置",
    val type:Int=0,
    val level:Int=0,
    var avatar:String="没有设置",
    var sex:Boolean=false
):BmobUser()