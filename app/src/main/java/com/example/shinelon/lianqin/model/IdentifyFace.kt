package com.example.shinelon.lianqin.model

/**
 * Created by HB on 2018/2/15.
 */
data class IdentifyFace(val log_id:Long,val result_num:Int,val result: List<Result>)
data class Result(val group_id:String,val uid:String,val user_info:String,val scores:List<Float>)
