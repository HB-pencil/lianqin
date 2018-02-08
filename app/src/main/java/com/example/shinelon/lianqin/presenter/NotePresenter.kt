package com.example.shinelon.lianqin.presenter

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.shinelon.lianqin.helper.DataBase
import com.example.shinelon.lianqin.model.AllNoteInfos
import com.example.shinelon.lianqin.model.NoteInfos
import com.example.shinelon.lianqin.view.BaseView
import com.example.shinelon.lianqin.view.NoteView

/**
 * Created by Shinelon on 2018/2/7.
 */
class NotePresenter(var db: SQLiteDatabase?): BasePresenter {
    var view: NoteView? = null
    override fun setView(baseView: BaseView?) {
        view = baseView as NoteView
    }

    fun queryItem(table: String,colums: Array<String>?,selection: String?,selectionArgs: Array<String>?,
                  groupBy: String?,having: String?,orderBy: String?){
        val cursor = db!!.query(table,colums,selection,selectionArgs,groupBy,having,orderBy)
        if(!AllNoteInfos.list.isEmpty()){
            AllNoteInfos.list.clear()
        }
        var i = 0
        while (cursor.moveToNext()){
            val noteItem = NoteInfos(cursor.getString(1),cursor.getString(2),cursor.getString(0))
            AllNoteInfos.list.add(noteItem)
            i++
        }
        Log.e("查询数据库条目","成功共${i}条")
        cursor.close()
    }


    fun deleteItem(table: String,where: String,whereArgs: Array<String>){
        val s = db!!.delete(DataBase.table,where,whereArgs)
        if(s>=0) Log.e("删除数据库指定条目","成功")
    }

    fun updateItem(table: String,values: ContentValues,where: String,whereArgs: Array<String>){
       val s= db!!.update(table,values,where,whereArgs)
        if(s>=0) Log.e("更改数据库指定条目","成功")
    }

    override fun clearView() {
        view = null
    }
}