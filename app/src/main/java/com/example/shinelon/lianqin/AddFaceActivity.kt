package com.example.shinelon.lianqin

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.shinelon.lianqin.helper.StringTypeEvaluator
import kotlinx.android.synthetic.main.activity_add_face.*

/**
 * Created by Shinelon on 2018/2/15.
 */
class AddFaceActivity: AppCompatActivity(){

    var group_id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_face)
        setSupportActionBar(activity_add_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        group_id = intent.getStringExtra("banji")
        Log.w("addFaceAc","组别"+group_id)

        val animator = ValueAnimator.ofObject(StringTypeEvaluator(),"#FFFF0310","#00FFFFFF")
        animator.addUpdateListener {
            val value = it.animatedValue as String
            activity_add_cusview.color1 = value
            activity_add_cusview.invalidate()
        }
        animator.duration = 2000
        animator.repeatCount = -1
        animator.repeatMode = ValueAnimator.REVERSE
        animator.start()

        activity_add_kaoqin.setOnClickListener{
            val i = Intent(this,CameraActivity::class.java)
            i.putExtra("group_id",group_id)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }

        activity_add_gengxin.setOnClickListener{
            val i = Intent(this,RegisterOrUpdateFaceActivity::class.java)
            i.putExtra("group_id",group_id)
            i.putExtra("action_type","replace")
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }

        activity_add_zhuce.setOnClickListener{
            val i = Intent(this,RegisterOrUpdateFaceActivity::class.java)
            i.putExtra("group_id",group_id)
            i.putExtra("action_type","append")
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }

    }
}