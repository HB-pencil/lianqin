package com.example.shinelon.lianqin.model

/**
 * Created by Shinelon on 2018/3/3.
 */
data class LoginResult(val code: Int,val data: DataBean,val success: Boolean,val message: String,
                       val token: String)
data class DataBean(val college: Int,val email: String,val major: String,val name: String,
                    val phone: String,val status: Int,val type: Int,val userId: Int,val userNumber: String)