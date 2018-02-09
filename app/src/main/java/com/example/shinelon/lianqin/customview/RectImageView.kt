package com.example.shinelon.lianqin.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.*

/**
 * Created by Shinelon on 2018/2/9.
 */
class RectImageView: View{

    var w = 0F
    var h = 0F
    val paint = Paint()
    constructor(context: Context):this(context,null)
    constructor(context: Context,attributeSet: AttributeSet?):this(context,attributeSet,0)
    constructor(context: Context,attributeSet: AttributeSet?,def: Int):super(context,attributeSet,def)
    {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10F
        paint.isAntiAlias = true
        paint.color = Color.BLACK
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        h = measuredHeight.toFloat()
        w = measuredWidth.toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(0F,0F,w,h,paint)
    }

}