package com.example.shinelon.lianqin.model

/**
 * Created by Shinelon on 2018/3/3.
 */
data class ClassDetailsResult(val code: Int,val success: Boolean,val list: ListBean,val message: String)
data class ListBean(val endRow: Int,val firstPage: Int,val hasNextPage: Boolean,val hasPreviousPage: Boolean,
                    val isFirstPage: Boolean,val isLastPage: Boolean,val lastPage: Int,
                    val list: List<DetailsBean>,val navigatePages: Int,val navigatePageNums: List<Int>,
                    val nextPage: Int,val pageNum: Int,val pageSize: Int,val pages: Int,val prePage: Int,
                    val size: Int,val startRow: Int,val total: Int)
data class DetailsBean(val className: String,val classNum: Int,val courseCode: String,val courseCredit: Int,
                       val courseHour: Int,val courseId: Int,val courseName: String,val courseStyle: Int,
                       val courseType: Int,val createTime: String,val studentNum: Int,val teacherCourseId: Int,
                       val teacherNumber: String,val term: String,val updateTime: String)
