package com.example.shinelon.lianqin.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * Created by Shinelon on 2018/2/15.
 */
class ActionView: View{

    val paint = Paint()
    var color1:String = "#FFFF0310"

    constructor(context: Context):super(context)
    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet,0){
        paint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = w/3 * 4
        setMeasuredDimension(w,h)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawItem1(canvas)
        drawItem2(canvas)
        drawItem3(canvas)
    }

    fun drawItem1(canvas: Canvas?){
        paint.color = Color.parseColor(color1)
        paint.style = Paint.Style.FILL
        canvas?.drawRect(0F,0F,measuredWidth.toFloat(),(measuredHeight/4).toFloat(),paint)

        paint.strokeWidth = 2F
        paint.textSize = 50F
        paint.textAlign = Paint.Align.CENTER

        val font = paint.fontMetrics
        val h = (font.descent - font.ascent)/2
        val t = h - font.descent
        Log.d("字体下移",t.toString())
        val x = measuredWidth/2
        val y = measuredHeight/8F + t
        paint.color = Color.WHITE

        canvas?.drawText("我要考勤",x.toFloat(),y,paint)
    }

    fun drawItem2(canvas: Canvas?){
        paint.color = Color.parseColor(color1)
        paint.style = Paint.Style.FILL
        canvas?.drawRect(0F,measuredHeight/8*3F,measuredWidth.toFloat(),measuredHeight/8*5F,paint)

        paint.strokeWidth = 2F
        paint.textSize = 50F
        paint.textAlign = Paint.Align.CENTER

        val font = paint.fontMetrics
        val h = (font.descent - font.ascent)/2
        val t = h - font.descent
        Log.d("字体下移",t.toString())
        val x = measuredWidth/2
        val y = measuredHeight/2 + t
        paint.color = Color.WHITE

        canvas?.drawText("我要重置",x.toFloat(),y,paint)
    }

    fun drawItem3(canvas: Canvas?){
        paint.color = Color.parseColor(color1)
        paint.style = Paint.Style.FILL
        canvas?.drawRect(0F,(measuredHeight/4 * 3).toFloat(),measuredWidth.toFloat(),measuredHeight.toFloat(),paint)

        paint.strokeWidth = 2F
        paint.textSize = 50F
        paint.textAlign = Paint.Align.CENTER

        val font = paint.fontMetrics
        val h = (font.descent - font.ascent)/2
        val t = h - font.descent
        val x = measuredWidth/2
        val y = measuredHeight/8*7F + t
        paint.color = Color.WHITE

        canvas?.drawText("人脸注册",x.toFloat(),y,paint)
    }

}