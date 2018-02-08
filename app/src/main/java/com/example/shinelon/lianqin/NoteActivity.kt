package com.example.shinelon.lianqin

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.os.PersistableBundle
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.TextView
import com.example.shinelon.lianqin.helper.DataBase
import com.example.shinelon.lianqin.helper.ItemTouchCallback
import com.example.shinelon.lianqin.model.AllNoteInfos
import com.example.shinelon.lianqin.model.NoteInfos
import com.example.shinelon.lianqin.presenter.NotePresenter
import com.example.shinelon.lianqin.view.NoteView
import kotlinx.android.synthetic.main.activity_note_list.*
import kotlinx.android.synthetic.main.listview_notes.view.*
import java.util.*

/**
 * Created by Shinelon on 2018/2/6.
 */
class NoteActivity: AppCompatActivity(),NoteView {
    /**
     * 不要在构造方法里面调用getActivity(),getApplication()等方法
     */
    var app: MyApplication? = null
    var presenter: NotePresenter? = null
    var list = mutableListOf<NoteInfos>()
    var adapter: NoteAdapter? = null

    interface ActionListener{
        fun swipAction(position: Int)
        fun dragAction(start: Int,target: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        app = application as MyApplication
        presenter = NotePresenter(app!!.db)
        Log.e("application","$app")

        setSupportActionBar(activity_list_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        updateView()
        initRecycler(list)
        recycler_note.itemAnimator = DefaultItemAnimator()
        val itemHelper = ItemTouchHelper(ItemTouchCallback(adapter))
        itemHelper.attachToRecyclerView(recycler_note)
    }

    fun updateView(){
        Log.e("updateView","成功")
        if(list.isEmpty()){
            presenter!!.queryItem(DataBase.table,null,null,null,null,null,null)
            list = AllNoteInfos.list
        }
        if (adapter==null){
            adapter = NoteAdapter(list)
            recycler_note.layoutManager = LinearLayoutManager(this)
            recycler_note.adapter = adapter
        }else{
            adapter!!.notifyDataSetChanged()
        }

    }

    override fun onResume() {
        super.onResume()
        updateView()
    }


    fun initRecycler(list: MutableList<NoteInfos>){
        if (!list.isEmpty()) {
            text_list_note_tips.visibility = View.INVISIBLE
        }else{
            text_list_note_tips.visibility = View.VISIBLE
        }
    }

    override fun init() {

    }

    inner class NoteViewHolder(v: View): RecyclerView.ViewHolder(v){
        val textContent = v.text_list_note
        val textDate = v.text_notes_date
        var uuid = ""
        var index: Int = 0
        init {
            v.setOnClickListener {
                Log.e("主键和索引","${uuid}和${index}")
                startNoteDetails(index,uuid)
            }

            v.setOnLongClickListener{
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(300)
                true
            }
        }
    }

    fun startNoteDetails(index: Int,uuid: String){
        val i = Intent(this,NoteDetailsActivity::class.java)
        i.putExtra("newItem",false)
        i.putExtra("index",index)
        i.putExtra("uuid",uuid)
        startActivity(i)
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
    }

    inner class NoteAdapter(listNote: MutableList<NoteInfos>): RecyclerView.Adapter<NoteViewHolder>(),ActionListener{
        var listNotes = listNote
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NoteViewHolder {
            val view = layoutInflater.inflate(R.layout.listview_notes,parent,false)
            return NoteViewHolder(view)
        }

        override fun onBindViewHolder(holder: NoteViewHolder?, position: Int) {
            //倒序查看
            holder?.textContent?.text = listNotes[(listNotes.size-1)-position].text
            holder?.textDate?.text = listNotes[(listNotes.size-1)-position].date
            holder?.uuid = listNotes[(listNotes.size-1)-position].uuid
            holder?.index = (listNotes.size-1)-position
            Log.w("uuid",listNotes[(listNotes.size-1)-position].uuid)
        }

        override fun getItemCount(): Int = listNotes.size

        override fun dragAction(start: Int, target: Int) {
            Collections.swap(listNotes,start,target)
            notifyItemMoved(start,target)
            val p0 = listNotes[start]
            val p1 = listNotes[target]

            val v0 = ContentValues()
            v0.put("content",p1.text)
            v0.put("time",p1.date)

            val v1 = ContentValues()
            v1.put("content",p0.text)
            v1.put("time",p0.date)

            presenter!!.updateItem(DataBase.table,v0,"id=?", arrayOf(p0.uuid))
            presenter!!.updateItem(DataBase.table,v1,"id=?", arrayOf(p1.uuid))
        }

        override fun swipAction(position: Int) {
            listNotes.removeAt(position)
            notifyItemRemoved(position)
            val uuid = listNotes[position].uuid
            presenter!!.deleteItem(DataBase.table,"id=?", arrayOf(uuid))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_note_add,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_note -> {
                val i = Intent(this,NoteDetailsActivity::class.java)
                i.putExtra("newItem",true)
                startActivity(i)
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        presenter!!.clearView()
    }


}