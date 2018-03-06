package com.example.shinelon.lianqin.presenter

import android.util.Log
import com.example.shinelon.lianqin.helper.RetrofitHelper
import com.example.shinelon.lianqin.model.AllNoteInfos
import com.example.shinelon.lianqin.model.StudentDet
import com.example.shinelon.lianqin.model.StudentDetailsBean
import com.example.shinelon.lianqin.view.BaseView
import com.example.shinelon.lianqin.view.StudentDetailsView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Shinelon on 2018/3/4.
 */
class StudentDetailsPresenter: BasePresenter {
    var view : StudentDetailsView? = null
    override fun setView(baseView: BaseView?) {
        view = baseView as StudentDetailsView
    }

    override fun clearView() {
       view = null
    }

    fun initList(list: ArrayList<StudentDetailsBean>,studentId: String,teacherCourseId: Int){
        val retrofit = Retrofit.Builder()
                .baseUrl("http://119.29.193.41:81")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(RetrofitHelper::class.java)
        val call = service.queryCourseStudentDetails(studentId,teacherCourseId,AllNoteInfos.schoolToken)
        call.enqueue(object :Callback<StudentDet>{
            override fun onFailure(call: Call<StudentDet>?, t: Throwable?) {
                Log.e("查询学生信息",t.toString())
                view?.init()
            }

            override fun onResponse(call: Call<StudentDet>?, response: Response<StudentDet>?) {
                Log.e("查询学生信息",response.toString())
                val result = response!!.body()
                if(result!!.code==200){
                    result.data.classTotalList.forEach{
                        list.add(it)
                    }
                    view?.init()
                }
                val rs = result.data.courseTotal
                view?.init(rs.originalClass,rs.absenceNum.toString(),rs.lateNum.toString(),
                        rs.leaveNum.toString(),rs.attendanceNum.toString())
            }
        })
    }
}