package caiflyy.pjy.today.data.weather

import cn.bmob.v3.BmobObject

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.data.weather
 * 日期：2019/4/25
 * 描述：
 * @author 蔡葳
 */
data class StudyClassification(val parentID:String,
                               val classificationName:String,
                               val imgPath:String,
                               val child:Int,
                               val video:String,
                               val desc:String): BmobObject()