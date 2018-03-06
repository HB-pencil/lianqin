package com.example.shinelon.lianqin.presenter

import android.os.Handler
import android.util.Log
import com.example.shinelon.lianqin.helper.RetrofitHelper
import com.example.shinelon.lianqin.model.AllNoteInfos
import com.example.shinelon.lianqin.model.CourseClassDetails
import com.example.shinelon.lianqin.view.BaseView
import com.example.shinelon.lianqin.view.RecordClassDetailsView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Shinelon on 2018/3/4.
 */
class RecordClassDetailsPresenter: BasePresenter {
    var view: RecordClassDetailsView? = null
    override fun setView(baseView: BaseView?) {
        view = baseView as RecordClassDetailsView
    }

    override fun clearView() {
        view = null
    }

    fun initList(courseClassId: Int){
        val retrofit = Retrofit.Builder()
                .baseUrl("http://119.29.193.41:81")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(RetrofitHelper::class.java)
        val call = service.queryCourseClassDetails(courseClassId,AllNoteInfos.schoolToken)
        call.enqueue(object :Callback<CourseClassDetails>{
            override fun onFailure(call: Call<CourseClassDetails>?, t: Throwable?) {
                Log.e("查询多个学生",t.toString())
                view?.initList(null,null,null)
                view?.init()
            }

            override fun onResponse(call: Call<CourseClassDetails>?, response: Response<CourseClassDetails>?) {
                Log.e("查询多个学生", response?.body().toString())
                val rs = response!!.body()
                if(rs!!.code==200){
                    val list1 = rs.data.lateStudentList
                    val list2 = rs.data.absenceStudentList
                    val list3 = rs.data.leaveStudentList
                    view?.initList(list1,list2,list3)
                    Log.e("list集合",list1.toString())
                    view?.initTeacherCourseId(rs.data.classTotal.teacherCourseId)
                    view?.init()
                }
            }
        })
    }
}