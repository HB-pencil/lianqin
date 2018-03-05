package com.example.shinelon.lianqin.presenter

import android.app.ProgressDialog
import android.util.Log
import com.example.shinelon.lianqin.MyApplication
import com.example.shinelon.lianqin.helper.RetrofitHelper
import com.example.shinelon.lianqin.model.AllNoteInfos
import com.example.shinelon.lianqin.model.Register
import com.example.shinelon.lianqin.view.BaseView
import com.example.shinelon.lianqin.view.RegiOrUpdaView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Shinelon on 2018/2/17.
 */
class RegiOrUpdatePresener: BasePresenter{
    var view: RegiOrUpdaView? = null

    override fun setView(baseView: BaseView?) {
        view = baseView as RegiOrUpdaView
    }

    fun registerFace(group_id: String,uid: String,user_info: String,image: String,type: String){
        view!!.showProgress()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://aip.baidubce.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(RetrofitHelper::class.java)
        val token = RequestBody.create(null,AllNoteInfos.token)
        val _uid = RequestBody.create(null,uid)
        val _user_info = RequestBody.create(null,user_info)
        val _group_id = RequestBody.create(null,group_id)
        val _actiontype = RequestBody.create(null,type)

        val _image = RequestBody.create(null,image)
        val call = service.registerFace(token,_uid,_user_info,_group_id,_image,_actiontype)
        call.enqueue(object :Callback<Register>{
            override fun onResponse(call: Call<Register>?, response: Response<Register>?) {
                view!!.dismissProgress()
                Log.e("onResponse",response?.body().toString())
                val result = response?.body()
                if (result!=null && result.error_code==0L){
                    view!!.showSuccessDialog()
                }else{
                    view!!.showFailureDialog(result!!.error_msg)
                }
            }

            override fun onFailure(call: Call<Register>?, t: Throwable?) {
                view!!.dismissProgress()
                Log.e("onFailure",t.toString())
                view!!.showFailureDialog("")
            }
        })
    }



    override fun clearView() {
        view = null
    }
}