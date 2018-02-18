package com.example.shinelon.lianqin.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log

/**
 * Created by Shinelon on 2018/2/18.
 */
class MyDialog: DialogFragment(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //这里传参获取
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val msg = arguments!!.getString("message")
        Log.d("msg",msg)
        val dialog = AlertDialog.Builder(activity)
                .setTitle("结果")
                .setMessage(msg)
                .setPositiveButton("确定") { dialogInterface, i ->
                }
                .create()
        return dialog
    }

    companion object {
        fun newInstance(msg1:String):MyDialog{
            val dialogFrg = MyDialog()
            val bundle = Bundle()
            bundle.putString("message",msg1)
            dialogFrg.arguments = bundle
            return dialogFrg
        }
    }

}