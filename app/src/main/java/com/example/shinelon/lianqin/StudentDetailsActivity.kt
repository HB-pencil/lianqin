package com.example.shinelon.lianqin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import com.example.shinelon.lianqin.model.StudentDet
import kotlinx.android.synthetic.main.activity_student_details.*
import kotlinx.android.synthetic.main.list_student_details.view.*

/**
 * Created by Shinelon on 2018/2/8.
 */
class StudentDetailsActivity: AppCompatActivity() {
    var img: ImageView? = null
    var textId: TextView? = null
    var textMajor: TextView? = null
    var que: TextView? = null
    var chi: TextView? = null
    var chu: TextView? = null
    var qin: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)
        setSupportActionBar(activity_student_details_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        img = student_image
        textId = student_id
        textMajor = student_major
        que = student_queqin
        chi = student_chidao
        chu = student_chuqin
        qin = student_qinjia

        val id = intent.getStringExtra("studentId")
        textId?.text = id


        val c1 = StudentDet("2017-08-15","15:30","第七周","出勤")
        val c2 = StudentDet("2017-08-15","15:30","第七周","出勤")
        val adapter = Adapter(arrayListOf(c1,c2))

        recycler_student.layoutManager = LinearLayoutManager(this)
        recycler_student.adapter = adapter
    }

    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        val date  = v.student_date
        val time = v.student_time
        val week = v.student_week
        val record = v.student_record
    }

    inner class Adapter(l: ArrayList<StudentDet>): RecyclerView.Adapter<ViewHolder>(){
        val list = l
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val v = layoutInflater.inflate(R.layout.list_student_details,parent,false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            holder?.date?.text = list[position].date
            holder?.time?.text = list[position].time
            holder?.week?.text = list[position].week
            holder?.record?.text = list[position].record
        }

        override fun getItemCount() = list.size
    }

}