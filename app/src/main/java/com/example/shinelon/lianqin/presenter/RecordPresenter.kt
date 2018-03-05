package com.example.shinelon.lianqin.presenter

import android.os.Handler
import android.util.Log
import com.example.shinelon.lianqin.helper.RetrofitHelper
import com.example.shinelon.lianqin.model.AllNoteInfos
import com.example.shinelon.lianqin.model.ClassDetails
import com.example.shinelon.lianqin.model.RecordTotal
import com.example.shinelon.lianqin.model.TotalSim
import com.example.shinelon.lianqin.view.BaseView
import com.example.shinelon.lianqin.view.RecordView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Shinelon on 2018/3/4.
 */
class RecordPresenter: BasePresenter {
    var recordView: RecordView? = null
    override fun setView(baseView: BaseView?) {
        recordView = baseView as RecordView
    }

    override fun clearView() {
        recordView = null
    }

    fun initList(list: MutableList<ClassDetails>,id: Int){
        val retrofit = Retrofit.Builder()
                .baseUrl("http://119.29.193.41:81")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(RetrofitHelper::class.java)
        val call = service.queryAllDetails(id,AllNoteInfos.schoolToken)
        call.enqueue(object :Callback<RecordTotal>{
            override fun onFailure(call: Call<RecordTotal>?, t: Throwable?) {
                Log.e("查询详细",t.toString())
                recordView?.updateHeader(0,0,0,0,0)
                recordView?.init()
            }

            override fun onResponse(call: Call<RecordTotal>?, response: Response<RecordTotal>?) {
                Log.e("查询详细", response!!.body().toString())
                val rs = response.body()
                if (rs!!.code==200){
                    val t = rs.data.courseTotal
                    recordView?.setTeacherCourseId(t.teacherCourseId)
                    rs.data.classTotalList.forEach {
                        val c = ClassDetails(it.createTime,"",it.studentNum,it.attendanceNum,it.absenceNum,
                                it.lateNum,it.leaveNum)
                        list.add(c)
                    }

                    recordView?.updateHeader(t.absenceNum+t.lateNum+t.leaveNum,t.hasClassNum,t.absenceNum,t.lateNum,t.leaveNum)
                    recordView?.init()
                }
            }
        })

    }


}