package com.example.shinelon.lianqin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.shinelon.lianqin.presenter.LoginPresenter
import com.example.shinelon.lianqin.view.LoginView
import kotlinx.android.synthetic.main.login_layout.*

/**
 * Created by HB on 2018/1/4.登录界面Activity，作为View
 */
class LoginActivity: AppCompatActivity(),LoginView {
    var presenter: LoginPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        presenter = LoginPresenter()
        presenter?.setView(this)

        login_bt.setOnClickListener { presenter?.checkAccount() }
    }

    override fun init() {

    }

    override fun loginSystem() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        Log.e("登录","成功登录")
    }

    override fun skipToForgetActivity() {

    }

    override fun skipToRegisterActivity() {

    }

    override fun onDestroy(){
        super.onDestroy()
        presenter?.clearView()
    }
}