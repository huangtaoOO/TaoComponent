package com.example.base.entity.home

import androidx.annotation.Keep

/**
 * Author: huangtao
 * Date: 2023/3/11
 * Desc: 文章实体类
 *
 * eg:
{
"adminAdd": false,
"apkLink": "",
"audit": 1,
"author": "",
"canEdit": false,
"chapterId": 502,
"chapterName": "自助",
"collect": false,
"courseId": 13,
"desc": "",
"descMd": "",
"envelopePic": "",
"fresh": false,
"host": "",
"id": 25946,
"isAdminAdd": false,
"link": "https://juejin.cn/post/7207374216127103033",
"niceDate": "2023-03-07 22:27",
"niceShareDate": "2023-03-07 22:26",
"origin": "",
"prefix": "",
"projectLink": "",
"publishTime": 1678199265000,
"realSuperChapterId": 493,
"route": false,
"selfVisible": 0,
"shareDate": 1678199184000,
"shareUser": "鸿洋",
"superChapterId": 494,
"superChapterName": "广场Tab",
"tags": [],
"title": "学得懂的 Android Framework 教程&mdash;&mdash;玩转 AOSP 之系统 App 源码添加",
"type": 0,
"userId": 2,
"visible": 1,
"zan": 0
}
 */
@Keep
data class ArticleEntity(
    val id: Int,
    val title: String,
    val visible: Int,
    val shareUser: String,
    val link: String,
    val niceDate: String,
    val superChapterId: Int,
    val superChapterName: String
)