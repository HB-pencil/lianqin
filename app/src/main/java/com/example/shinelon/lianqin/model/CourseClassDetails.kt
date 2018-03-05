package com.example.shinelon.lianqin.model

import java.io.Serializable


/**
 * Created by Shinelon on 2018/3/4.
 */
data class CourseClassDetails(val code: Int,val data: DataBeanR,val success: Boolean,val message: String)
data class DataBeanR(val classTotal: ClassTotalBean,val lateStudentList: ArrayList<StudentBean>,val leaveStudentList: ArrayList<StudentBean>,
                     val absenceStudentList: ArrayList<StudentBean>)
data class ClassTotalBean(val courseClassId: Int,val teacherCourseId: Int,val studentNum: Int,val attendanceNum: Int,
                          val lateNum: Int,val leaveNum: Int,val absenceNum: Int,val attendanceStatus: Int,
                          val classOrder: Int,val createTime: String)
data class StudentBean(val name: String,val studentNumber: String): Serializable