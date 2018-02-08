package com.example.shinelon.lianqin.fragment

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shinelon.lianqin.MyApplication
import com.example.shinelon.lianqin.R
import com.example.shinelon.lianqin.helper.DataBase
import com.example.shinelon.lianqin.model.AllNoteInfos
import com.example.shinelon.lianqin.model.NoteInfos
import com.example.shinelon.lianqin.presenter.NoteDetailsPrensenter
import com.example.shinelon.lianqin.view.NoteDetailsView
import kotlinx.android.synthetic.main.fragment_note_details.*
import kotlinx.android.synthetic.main.fragment_note_details.view.*
import kotlinx.android.synthetic.main.listview_notes.*
import kotlinx.android.synthetic.main.listview_notes.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

/**
 * Created by Shinelon on 2018/2/7.
 */
class NoteDetailsFragment: Fragment(),NoteDetailsView {
    var app: MyApplication? = null
    var dbSQL: SQLiteDatabase? = null
    var presenter: NoteDetailsPrensenter? = null
    var isNew = true
    var uuid: String = ""
    var time: String = ""
    var v: View? = null
    var index: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_note_details,container,false)

        app = activity!!.application as MyApplication
        dbSQL = app!!.db
        presenter = NoteDetailsPrensenter(dbSQL)
        isNew = activity!!.intent!!.getBooleanExtra("newItem",true)
        Log.e("是否新页面",isNew.toString())

        presenter!!.setView(this)
        if(!isNew){
            uuid = activity!!.intent.getStringExtra("uuid")
            index = activity!!.intent.getIntExtra("index",0)
            presenter!!.queryItem(DataBase.table,null,"id=?", arrayOf(uuid),null,null,null)
        }
        return v
    }

    override fun showData(t: String, content: String) {
        Log.w("Details",t+content)
        //除了Activity,其他都要View.childView
        v!!.edit_note_details.setText(content)
        v!!.text_note_details.text = t
        time = t
    }

    /**
     * onPause()Activity执行
     */
    override fun onPause() {
        super.onPause()
        val content = edit_note_details.text.toString()
        Log.e("content",content)

        if (isNew){
            val date = Date()
            val spf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            time = spf.format(date)
            val uuid = UUID.randomUUID().toString()

            val values = ContentValues()
            values.put("id",uuid)
            values.put("time",time)
            values.put("content",content)

            val n = presenter!!.insertItem(DataBase.table,null,values)
            //增加
            if(n!=null&&n>=0){
                val item = NoteInfos(content,time,uuid)
                AllNoteInfos.list.add(item)
            }

        }else{
            val values = ContentValues()
            values.put("content",content)
            var n = presenter!!.updateItem(DataBase.table,values,"id=?", arrayOf(uuid))
            val item = NoteInfos(content,time,uuid)
            //修改
            if(n!=null&&n>=0){
                AllNoteInfos.list.set(index,item)
            }
        }

    }

    override fun init() {

    }

    override fun onDetach() {
        super.onDetach()
        presenter!!.clearView()
    }
}