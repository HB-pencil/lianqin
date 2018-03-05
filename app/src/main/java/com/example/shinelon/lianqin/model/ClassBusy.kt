package com.example.shinelon.lianqin.model

/**
 * Created by Shinelon on 2018/3/3.
 */
data class ClassBusy(val code: Int,val data: DataBeanT,val success: Boolean,val message: String)
data class DataBeanT(val classOrder: Int,val courseClassId: Int,val teacherCourseId: Int)