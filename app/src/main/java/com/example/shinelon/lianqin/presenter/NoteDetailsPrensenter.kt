package com.example.shinelon.lianqin.presenter

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.text.Selection
import android.util.Log
import com.example.shinelon.lianqin.view.BaseView
import com.example.shinelon.lianqin.view.NoteDetailsView

/**
 * Created by Shinelon on 2018/2/7.
 */
class NoteDetailsPrensenter(var db: SQLiteDatabase?): BasePresenter {
    var noteView: NoteDetailsView? = null
    override fun setView(baseView: BaseView?) {
        noteView = baseView as NoteDetailsView
    }

    fun insertItem(table: String,nullColumns: String?,values: ContentValues): Long?{
        Log.e("插入","成功")
        return db?.insert(table,null,values)
    }

    fun queryItem(table: String,colums: Array<String>?,selection: String?,selectionArgs: Array<String>?,
    groupBy: String?,having: String?,orderBy: String?){
        val cursor = db!!.query(table,colums,selection,selectionArgs,groupBy,having,orderBy)
        cursor?.moveToFirst()
        val content = cursor.getString(1)
        val time = cursor.getString(2)
        Log.e("查询详细页面","成功")
        noteView?.showData(time,content)
        cursor.close()
    }

    fun updateItem(table: String,values: ContentValues,where: String,whereArgs: Array<String>): Int?{
        Log.e("修改","成功")
        return db?.update(table,values,where,whereArgs)
    }

    override fun clearView() {
        noteView = null
    }
}