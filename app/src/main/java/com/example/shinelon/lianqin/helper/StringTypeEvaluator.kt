package com.example.shinelon.lianqin.helper

import android.animation.TypeEvaluator

/**
 * Created by Shinelon on 2018/2/15.
 */
class StringTypeEvaluator: TypeEvaluator<String> {
    override fun evaluate(p0: Float, p1: String?, p2: String?): String {
        val startA = p1?.substring(1,3)
        val startR = p1?.substring(3,5)
        val startG = p1?.substring(5,7)
        val startB = p1?.substring(7,9)

        val endA = p2?.substring(1,3)
        val endR = p2?.substring(3,5)
        val endG = p2?.substring(5,7)
        val endB = p2?.substring(7,9)

        val a = Integer.parseInt(endA,16) - Integer.parseInt(startA,16)
        val r = Integer.parseInt(endR,16) - Integer.parseInt(startR,16)
        val g = Integer.parseInt(endG,16) - Integer.parseInt(startG,16)
        val b = Integer.parseInt(endB,16) - Integer.parseInt(startB,16)

        val cA = (Integer.parseInt(startA,16) + p0*a).toInt()
        val cR = (Integer.parseInt(startR,16) + p0*r).toInt()
        val cG = (Integer.parseInt(startG,16) + p0*g).toInt()
        val cB = (Integer.parseInt(startB,16) + p0*b).toInt()

        val alpha = getHexString(cA)
        val red = getHexString(cR)
        val green = getHexString(cG)
        val blue = getHexString(cB)

        return "#$alpha$red$green$blue"
    }

    fun getHexString(value:Int): String{
        var str = Integer.toHexString(value)
        if(str.length==1){
            str = "0"+str
        }
        return str
    }
}