package com.example.shinelon.lianqin.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Created by Shinelon on 2018/2/6.
 */
class NotesSQLiteHelper(context: Context)
    : SQLiteOpenHelper(context,DataBase.DATABASE_NAME,null,1) {

    val CREATE_TABLE = "create table ${DataBase.table}(" +
            "id varchar(50) primary key," +
            "content varchar(1000) not null," +
            "time varchar(25) not null)"
    override fun onCreate(p0: SQLiteDatabase?){
        p0?.execSQL(CREATE_TABLE)
        Log.e("数据库建立","成功")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}