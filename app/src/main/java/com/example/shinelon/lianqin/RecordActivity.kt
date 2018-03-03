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
import com.example.shinelon.lianqin.model.ClassInfos
import kotlinx.android.synthetic.main.activity_choose_class.*
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.android.synthetic.main.list_record.view.*
import kotlinx.android.synthetic.main.listview_choose_classs.view.*

/**
 * Created by Shinelon on 2018/2/8.
 */
class RecordActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        setSupportActionBar(activity_record_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val c1 = ClassInfos("17-1","16计算机科学与技术")
        val c2 = ClassInfos("17-1","16医学信息工程")
        val c3 = ClassInfos("17-1","15医学信息工程")
        val mList = mutableListOf<ClassInfos>(c1,c2,c3)
        recycler_record.layoutManager = LinearLayoutManager(this)
        recycler_record.adapter = RecordAdapter(mList)
    }
    private inner class RecordViewHolder(v: View): RecyclerView.ViewHolder(v){
        val semesterText = v.text_list_record
        val classText = v.text_list_record_class
        init {
            v.setOnClickListener {
                Toast.makeText(v.context,"点击了班级：${classText.text}", Toast.LENGTH_SHORT).show()
                jumpToRecordDetails(classText.text.toString())
            }
        }
    }

    /**
     * 跳转到详细班级情况界面
     */
    fun jumpToRecordDetails(semester: String){
        val intent = Intent(this,RecordDetailsActivity::class.java)
        intent.putExtra("semester",semester)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
    }

    private inner class RecordAdapter(l:MutableList<ClassInfos>): RecyclerView.Adapter<RecordViewHolder>(){
        val list: MutableList<ClassInfos> = l

        override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
            holder.semesterText?.text = list[position].semester
            holder.classText?.text = list[position].classes
        }
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecordViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_record,parent,false)
            return RecordViewHolder(view)
        }

        override fun getItemCount() = list.size
    }
}