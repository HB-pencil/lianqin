package com.example.shinelon.lianqin.helper

import com.example.shinelon.lianqin.model.*
import io.reactivex.Observable
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
     * 人脸库注册人脸，可以多组，一组多个用户，一个用户多张脸,重复注册将会追加/替换
     * 一个uid可以存在多个组中
     */
    @Multipart
    @POST("rest/2.0/face/v2/faceset/user/add")
    fun registerFace(@Part("access_token") token: RequestBody,@Part("uid") uid:RequestBody,@Part("user_info") info: RequestBody,
                     @Part("group_id") gid: RequestBody,@Part("images") img:RequestBody ,@Part("action_type") type: RequestBody): Call<Register>

    /**
     * 人脸库里面识别人脸，返回人脸库里边相似度最高，阈值80
     */
    @Multipart
    @POST("rest/2.0/face/v2/identify")
    fun recognizeFace(@Part("access_token")token: RequestBody,@Part("group_id")group_id: RequestBody,@Part("image")image: RequestBody): Observable<IdentifyFace>


    /**
     * 用户登录
     */
    @POST("/api/user/login")
    fun loginRequest(@Body body: RequestBody): Call<LoginResult>

    /**
     * 查看本学期所教班级
     */
    @GET("/api/teacher/{teacherNumber}/now/course/{pageSize}/{pageNum}")
    fun queryClassDetails(@Path("teacherNumber") teacherNumber: String,@Path("pageSize") pageSize: Int,
                          @Path("pageNum") pageNum: Int,@Header("token") token: String): Call<ClassDetailsResult>

    /**
     *查看是否有课
     */
    @GET("/api/course/{teacherCourseId}/status")
    fun isClassBusy(@Header("token") token: String,@Path("teacherCourseId") teacherCourseId: Int): Call<ClassBusy>

    /**
     * 记录考勤
     */
    @POST("/api/course/class/attendance")
    fun startRecord(@Body body: RequestBody,@Header("token") token: String): Call<RecordResult>

    /**
     * 查看课程总考勤总计+各节课考勤的列表
     */
    @GET("/api/course/{teacherCourseId}/statistics")
    fun queryAllDetails(@Path("teacherCourseId") teacherCourseId: Int,@Header("token") token: String): Call<RecordTotal>

    /**
     * 查看某课程考勤记录
     */
    @GET("/api/class/{courseClassId}/statistics")
    fun queryCourseClassDetails(@Path("courseClassId") courseClassId: Int,@Header("token") token: String): Call<CourseClassDetails>

    /**
     * 查看某学生的某课程的所有出勤情况
     */
    @GET("/api/student/{studentNumber}/course/{teacherCourseId}/statistics")
    fun queryCourseStudentDetails(@Path("studentNumber") studentNumber: String,@Path("teacherCourseId") teacherCourseId: Int,
                                  @Header("token") token: String): Call<StudentDet>
}
