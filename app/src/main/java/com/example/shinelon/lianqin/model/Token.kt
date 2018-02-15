package com.example.shinelon.lianqin.model

/**
 * Created by Shinelon on 2018/2/14.
 */
data class Token(val refresh_token: String,val expires_in: Long,val scope: String,val session_key:String,
                 val access_token: String,val session_secret: String)