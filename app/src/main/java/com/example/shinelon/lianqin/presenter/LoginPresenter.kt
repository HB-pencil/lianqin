package com.example.shinelon.lianqin.presenter

import android.util.Log
import com.example.shinelon.lianqin.helper.RetrofitHelper
import com.example.shinelon.lianqin.model.AllNoteInfos
import com.example.shinelon.lianqin.model.LoginResult
import com.example.shinelon.lianqin.view.BaseView
import com.example.shinelon.lianqin.view.LoginView
import com.example.shinelon.lianqin.view.MainView
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by HB on 2018/1/4.
 */
class LoginPresenter: BasePresenter {
    var view: LoginView? = null
    override fun setView(baseView: BaseView?) {
        view = baseView as LoginView
    }

    fun checkAccount(account: String,password: String){
        val retrofit = Retrofit.Builder()
                .baseUrl("http://119.29.193.41:81")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(RetrofitHelper::class.java)
        val json = "{\"userNumber\":\"$account\",\"password\":\"$password\"}"
        val body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),json)
        val call = service.loginRequest(body)
        call.enqueue(object :Callback<LoginResult>{
            override fun onResponse(call: Call<LoginResult>?, response: Response<LoginResult>?) {
                Log.e("登录",response?.body().toString()+Thread.currentThread())
                val result = response!!.body()
                if(result!!.code==200){
                    AllNoteInfos.teacherNum = result.data.userNumber
                    AllNoteInfos.teacherName = result.data.name
                    AllNoteInfos.schoolToken = result.token
                    view?.loginSystem()
                }else{
                    view?.loginFail()
                }
            }

            override fun onFailure(call: Call<LoginResult>?, t: Throwable?) {
                view?.loginFail()
            }
        })
    }

    override fun clearView() {
        view = null
    }
}