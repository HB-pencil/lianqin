package com.example.shinelon.lianqin.helper

import com.example.shinelon.lianqin.model.IdentifyFace
import com.example.shinelon.lianqin.model.Register
import com.example.shinelon.lianqin.model.Token
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Shinelon on 2018/2/14.人脸识别相关Api调用（注册/追加，识别，更新），其他功能暂未考虑
 */
interface RetrofitHelper {
    /**
     * 获取百度人脸识别token
     */
    @FormUrlEncoded
    @POST("oauth/2.0/token")
    fun getToken(@Field("grant_type") type: String,@Field("client_id") api_key: String,@Field("client_secret") secret_key:String): retrofit2.Call<Token>

    /**
     * 人脸库注册人脸，可以多组，一组多个用户，一个用户多张脸,重复注册将会追加
     * 一个uid可以存在多个组中
     */
    @Multipart
    @POST("rest/2.0/face/v2/faceset/user/add")
    fun registerFace(@Part("access_token") token: RequestBody,@Part("uid") uid:RequestBody,@Part("user_info") info: RequestBody,
                     @Part("group_id") gid: RequestBody,@Part("images")img: MultipartBody.Part ,@Part("action_type")type: RequestBody): Call<Register>

    /**
     * 人脸库里面识别人脸，返回人脸库里边相似度最高，阈值80
     */
    @Multipart
    @POST("rest/2.0/face/v2/identify")
    fun recognizeFace(@Part("access_token") token: ResponseBody,@Part("group_id") group_id:ResponseBody,@Part("image") image:Array<Byte>): Call<IdentifyFace>

    /**
     *人脸更新，替换原来用户uid的所有人脸
     */
    @Multipart
    @POST("rest/2.0/face/v2/faceset/user/update")
    fun updateFace(@Part("access_token") token: ResponseBody,@Part("uid") uid:ResponseBody,@Part("image") image:Array<Byte>): Call<Register>

}
