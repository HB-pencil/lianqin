package com.example.shinelon.lianqin.model

import java.io.Serializable

/**
 * Created by Shinelon on 2018/2/10.
 */
data class StudentDet(val code: Int,val data: DataStudentBean,val success: Boolean,val message: String)
data class DataStudentBean(val classTotalList: MutableList<StudentDetailsBean>,val courseTotal: CourseTotalBean)
data class StudentDetailsBean(val studentCourseId: Int,val studentNumber: String,val teacherCourseId: Int,
                             val courseClassId: Int,val weekNum:Int,val calssOrder: Int,val attendanceStatus: Int,
                             val attendanceTime: String,val createTime: String)
data class CourseTotalBean(val studentCourseId: Int,val studentNumber: String,val studentName: String,val portrait: String,
                           val originalClass: String,val teacherCourseId: Int,val courseName: String,val className: String,
                           val term: String,val attendanceNum: Int,val lateNum: Int,val leaveNum: Int,val absenceNum: Int,
                           val classNum: Int,val hasClassNum: Int,val status: Int )