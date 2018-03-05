package com.example.shinelon.lianqin

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ProgressBar
import com.example.shinelon.lianqin.helper.RetrofitHelper
import com.example.shinelon.lianqin.helper.StringTypeEvaluator
import com.example.shinelon.lianqin.model.AllNoteInfos
import kotlinx.android.synthetic.main.activity_add_face.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.*

/**
 * Created by Shinelon on 2018/2/15.
 */
class AddFaceActivity: AppCompatActivity(){

    var group_id: String = ""
    var isBusy = false
    var courseClassId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_face)
        setSupportActionBar(activity_add_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        group_id = intent.getStringExtra("banji")
        Log.w("addFaceAc","组别"+group_id)


        activity_add_kaoqin.setOnClickListener{
            if (!isBusy){
                showWarning()
            }else{
                val i = Intent(this,CameraActivity::class.java)
                i.putExtra("group_id",group_id)
                i.putExtra("courseClassId",courseClassId)
                i.putExtra("isBusy",isBusy)
                startActivity(i)
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            }
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

        val pool = ThreadPoolExecutor(1,1,0,TimeUnit.MILLISECONDS,LinkedBlockingQueue<Runnable>())
        pool.submit{
            val retrofit = Retrofit.Builder()
                    .baseUrl("http://119.29.193.41:81")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            val service = retrofit.create(RetrofitHelper::class.java)
            val result = service.isClassBusy(AllNoteInfos.schoolToken,intent.getIntExtra("teacherCourseId",0))
            val response = result.execute()
            val rs = response.body()
            Log.e("查询有无课",rs.toString())
            if(rs!!.code==200){
                isBusy = true
                courseClassId = rs.data.courseClassId
            }
        }
        Thread.sleep(1200)
    }

    fun showWarning(){
        val dialog = AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage("查不到您今天的课程，如果仍继续考勤，考勤结果将不会被记录！")
                .setNegativeButton("取消"){
                    _, _ ->  finish()
                }
                .setPositiveButton("继续"){
                    _,_ ->  val i = Intent(this,CameraActivity::class.java)
                    i.putExtra("group_id",group_id)
                    i.putExtra("courseClassId",courseClassId)
                    i.putExtra("isBusy",isBusy)
                    startActivity(i)
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                }
                .create().show()
    }
}