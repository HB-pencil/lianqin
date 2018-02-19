package com.example.shinelon.lianqin.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout

/**
 * Created by HB on 2017/12/20.
 */
class IndicateView: View {
    private val mPaint: Paint = Paint()
    private var rect: Rect? = null
    private var rectf: RectF? = null
    constructor(context: Context,r: Rect):this(context,null){
        rect = r
    }
    constructor(context: Context,attributeSet: AttributeSet?):this(context,attributeSet,0)
    constructor(context: Context,attributeSet: AttributeSet?,def:Int):super(context,attributeSet,def){
        mPaint.isAntiAlias = true
        mPaint.color = Color.RED
        mPaint.textAlign = Paint.Align.CENTER
        mPaint.textSize = 50F
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val p = parent as FrameLayout
        val w = p.layoutParams.width
        val h = p.layoutParams.height
        setMeasuredDimension(w,h)
        Log.e("View","$w 和 $h")
    }

    fun createRect(){
        val l = rect!!.left * width/2000F
        val t = rect!!.top * height/2000F
        val r = rect!!.right * width/2000F
        val b = rect!!.bottom * height/2000F
        rectf = RectF(l-(r-l)/2F,t,r+(r-l)/2F,b)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        createRect()
        canvas?.save()
        canvas?.translate(width/2F,height/2F)
        mPaint.style = Paint.Style.FILL
        canvas?.drawText("人脸识别中",rectf!!.left+rectf!!.width()/2,rectf!!.top-20,mPaint)
        mPaint.strokeWidth = 5F
        mPaint.style = Paint.Style.STROKE
        canvas?.drawRect(rectf,mPaint)
        canvas?.restore()
        Log.e("自定义方框","左${rectf?.left}顶${rectf?.top}右${rectf?.right}下${rectf?.bottom}")
    }
}