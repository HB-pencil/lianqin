package com.example.shinelon.lianqin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.shinelon.lianqin.model.ClassDetails
import com.example.shinelon.lianqin.model.ClassInfos
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.android.synthetic.main.activity_record_details.*
import kotlinx.android.synthetic.main.list_record_details.view.*
import kotlinx.android.synthetic.main.listview_choose_classs.view.*

/**
 * Created by Shinelon on 2018/2/8.
 */
class RecordDetailsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_details)
        setSupportActionBar(activity_record_details_toolbar)
        supportActionBar?.title = "16计算机"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val c1 = ClassDetails("17-1","16计算机",90,88,5,6,4)
        val c2 = ClassDetails("17-1","16医工",50,45,5,4,3)
        val mList = mutableListOf<ClassDetails>(c1,c2)
        recycler_record_details.layoutManager = LinearLayoutManager(this)
        recycler_record_details.adapter = RecordAdapter(mList)
    }
    private inner class RecordViewHolder(v: View): RecyclerView.ViewHolder(v){
        val semester = v.record_time
        val week = v.record_week
        val number = v.number_class
        val chuqin = v.chuqin
        val queqin = v.queqin
        val chidao = v.chidao
        val qinjia = v.qinjia
        val button = v.bt_record_details

        init {
            button.setOnClickListener {
                Toast.makeText(v.context,"点击了班级共有人数${number.text}", Toast.LENGTH_SHORT).show()
                jumpToRecordDetailsMore()
            }
        }
    }

    /**
     * 跳转到详细界面
     */
    fun jumpToRecordDetailsMore(){
        val intent = Intent(this,RecordClassDetailsActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
    }

    private inner class RecordAdapter(l:MutableList<ClassDetails>): RecyclerView.Adapter<RecordViewHolder>(){
        val list: MutableList<ClassDetails> = l

        override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
            holder.semester.text = list[position].time
            holder.number.text = list[position].number.toString()
            holder.chidao.text = list[position].chidao.toString()
            holder.chuqin.text = list[position].chuqin.toString()
            holder.queqin.text = list[position].queqin.toString()
            holder.qinjia.text = list[position].qinjia.toString()
            holder.week.text = list[position].week
        }
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecordViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_record_details,parent,false)
            return RecordViewHolder(view)
        }

        override fun getItemCount() = list.size
    }
}