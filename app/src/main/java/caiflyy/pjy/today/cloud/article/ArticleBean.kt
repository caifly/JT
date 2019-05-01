package caiflyy.pjy.today.cloud.article

import cn.bmob.v3.BmobObject
import cn.bmob.v3.datatype.BmobRelation

/**
 * 项目名称：JT
 * 包名：caiflyy.pjy.today.cloud.article
 * 日期：2019/4/24
 * 描述：
 * @author 蔡葳
 */
data class ArticleBean(val title:String,
                       val content:String,
                       val imgPath:String,
                       var likeCount:Int,
                       var isLike:Boolean,
                       var likes:BmobRelation):BmobObject()