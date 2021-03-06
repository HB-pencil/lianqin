package com.example.shinelon.lianqin.presenter

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.example.shinelon.lianqin.helper.RetrofitHelper
import com.example.shinelon.lianqin.model.AllNoteInfos
import com.example.shinelon.lianqin.model.ClassDetailsResult
import com.example.shinelon.lianqin.model.ClassInfos
import com.example.shinelon.lianqin.model.Token
import com.example.shinelon.lianqin.view.BaseView
import com.example.shinelon.lianqin.view.MainView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by HB on 2017/12/11.
 */

class MainPresenter : BasePresenter {
    private var mainView: MainView? = null

    override fun setView(baseView: BaseView?) {
        mainView = baseView as MainView
        setTokent()
    }

    fun init(){
        mainView?.init()
    }


    fun setTokent(){
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://aip.baidubce.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service= retrofit.create(RetrofitHelper::class.java)
        val respon = service.getToken("client_credentials","FahVw2KyLbTOpSgbWREavdRA","U8uUpwIsrnBCVIOiMyoai3ObiGvWMVfy")
        respon.enqueue(object: Callback<Token>{
            override fun onResponse(call: Call<Token>?, response: Response<Token>?) {
                Log.e("onResponse",""+response?.body()?.expires_in)
                val result = response?.body()
                AllNoteInfos.token = result?.access_token
                Log.e("result",response?.body().toString())
            }

            override fun onFailure(call: Call<Token>?, t: Throwable?) {
                Log.e("onFailure",t.toString())
            }
        })
    }

    override fun clearView() {
        mainView = null
    }

    fun getClassDetailsResult(){
        val retrofit = Retrofit.Builder()
                .baseUrl("http://119.29.193.41:81")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(RetrofitHelper::class.java)
        val call = service.queryClassDetails(AllNoteInfos.teacherNum,5,1,AllNoteInfos.schoolToken)
        call.enqueue(object :Callback<ClassDetailsResult>{
            override fun onFailure(call: Call<ClassDetailsResult>?, t: Throwable?) {
                Log.e("异常",t.toString())
            }

            override fun onResponse(call: Call<ClassDetailsResult>?, response: Response<ClassDetailsResult>?) {
                Log.e("查询班级",response!!.body().toString())
                val result = response.body()
                if(result!!.code==200){
                    result.list.list.forEach {
                        val c = ClassInfos(it.term,it.className,it.courseName,it.teacherCourseId)
                        AllNoteInfos.classInfoList.add(c)
                    }
                }else{
                    Log.e("失败",result.toString())
                }
            }
        })
    }
}
