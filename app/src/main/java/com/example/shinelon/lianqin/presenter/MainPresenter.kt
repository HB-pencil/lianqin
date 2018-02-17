package com.example.shinelon.lianqin.presenter

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.example.shinelon.lianqin.helper.RetrofitHelper
import com.example.shinelon.lianqin.model.AllNoteInfos
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
    }

    fun init(){
        mainView?.init()
        checkTokenAndReturn()
    }

    fun checkTokenAndReturn(){
        val pre = PreferenceManager.getDefaultSharedPreferences(mainView as? Context)
        val token = pre.getString("token","没有")
        val time = pre.getLong("time",0L)
        if ("没有".equals("token")&&(time==0L)){
            getBtokentAndSave()
        }else{
            val current = System.currentTimeMillis()
            val days = (current - time)/1000F/3600/24
            if (days>=15F) getBtokentAndSave()
            else Log.w("检查","token没有过期:$token")
        }
        AllNoteInfos.token = token
    }


    fun getBtokentAndSave(){
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
                Log.e("result",response?.body().toString())

                val pre = PreferenceManager.getDefaultSharedPreferences(mainView as? Context)
                val edit = pre.edit()
                edit.putString("token",result?.access_token)
                val time = System.currentTimeMillis()
                edit.putLong("time",time)
                edit.apply()
            }

            override fun onFailure(call: Call<Token>?, t: Throwable?) {
                Log.e("onFailure",t.toString())
            }
        })
    }

    override fun clearView() {
        mainView = null
    }
}
