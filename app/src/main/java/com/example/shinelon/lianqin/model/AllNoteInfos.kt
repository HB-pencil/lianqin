package com.example.shinelon.lianqin.model


import android.os.Handler
import kotlin.properties.Delegates

/**
 * Created by Shinelon on 2018/2/6.
 */
object AllNoteInfos {
    var list = mutableListOf<NoteInfos>()
    var token: String?= ""
    var teacherNum: String by Delegates.notNull()
    var teacherName: String by Delegates.notNull()
    var schoolToken: String by Delegates.notNull()
    var classInfoList = mutableListOf<ClassInfos>()
}