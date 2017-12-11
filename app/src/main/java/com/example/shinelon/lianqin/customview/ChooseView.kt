package com.example.shinelon.lianqin.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.ImageButton
import android.widget.ImageView
import com.example.shinelon.lianqin.R

/**
 * Created by Shinelon on 2017/12/11.
 */
class ChooseView: ImageButton {
    private val mPaint = Paint()
    constructor(context: Context?):this(context,null)
    constructor(context: Context?,attributeSet: AttributeSet?):this(context,attributeSet,0)
    constructor(context: Context?,attributeSet: AttributeSet?,defStyle: Int):super(context,attributeSet,defStyle)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        val wMode = MeasureSpec.getMode(widthMeasureSpec)
        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        if(wMode == MeasureSpec.AT_MOST){
            width = 280
            height = 280
        }
        setMeasuredDimension(width,height)
    }

    override fun onDraw(canvas: Canvas?) {
        //onDraw调用次数不定
        mPaint.color = resources.getColor(R.color.colorPrimary)
        mPaint.style = Paint.Style.FILL
        canvas?.drawCircle(width.toFloat()/2,height.toFloat()/2,(width-30F)/2,mPaint)
        mPaint.color = Color.WHITE
        mPaint.isAntiAlias = true
        mPaint.textSize = 50F
        mPaint.textAlign = Paint.Align.CENTER
        canvas?.drawText("选择班级",width.toFloat()/2,height.toFloat()/2F+25F,mPaint)
    }
}