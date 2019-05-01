package caiflyy.pjy.today.cloud.article

import cn.bmob.v3.BmobObject

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.network
 * 日期：2019/4/24
 * 描述：
 * @author 蔡葳
 */
data class NewsComments(val newsId:String,
                        val newsCommentContent:String,
                        val data:Long,
                        val commentUserId:String,
                        val commentUserName:String,
                        val commentPortraitPath:String):BmobObject()