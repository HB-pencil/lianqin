package com.example.shinelon.lianqin.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.example.shinelon.lianqin.R
import kotlinx.android.synthetic.main.camera_layout.*
import org.jetbrains.anko.centerInParent

/**
 * Created by Shinelon on 2017/12/11.
 */
class assitantView: View {
    val paint = Paint()
    val path = Path()
    constructor(context: Context):this(context,null)
    constructor(context: Context,attributeSet: AttributeSet?):this(context,attributeSet,0)
    constructor(context: Context,attributeSet: AttributeSet?,def: Int):super(context,attributeSet,def){
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5F
        paint.color = resources.getColor(R.color.colorPrimary)
        paint.pathEffect = DashPathEffect(floatArrayOf(20F,10F),0F)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val dm = resources.displayMetrics
        val w = dm.widthPixels
        val h = dm.heightPixels
        val p =layoutParams as RelativeLayout.LayoutParams
        p.centerInParent()
        p.width = (w*0.7).toInt()
        p.height = p.width
        setMeasuredDimension(p.width,p.height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        path.moveTo(5F,5F)
        path.lineTo(width-5F,5F)
        path.lineTo(width-5F,height-5F)
        path.lineTo(5F,height-5F)
        //path.addRect(5F,5F,width-5F,height-5F,Path.Direction.CCW)
        path.close()
        canvas?.drawPath(path,paint)
    }

}