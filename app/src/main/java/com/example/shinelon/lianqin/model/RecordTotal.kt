package com.example.shinelon.lianqin.model

/**
 * Created by Shinelon on 2018/3/4.
 */
data class RecordTotal(val code: Int,val data: DataBeanW,val success: Boolean,val message: String)
data class DataBeanW(val classTotalList: List<CourseRecord>,val courseTotal: TotalBean)
data class CourseRecord(val absenceNum: Int,val attendanceNum: Int,val attendanceStatus: Int,val classOrder:Int,
                        val courseClassId: Int,val lateNum: Int,val leaveNum: Int,val studentNum: Int,
                        val teacherCourseId: Int,val createTime: String)
data class TotalBean(val teacherCourseId: Int,val studentNum: Int,val classNum: Int,val hasClassNum: Int,
                     val allAttendanceNum: Int,val notAttendanceNum: Int,val lateNum: Int,val leaveNum: Int,val absenceNum: Int,val attendanceStatus: Int)