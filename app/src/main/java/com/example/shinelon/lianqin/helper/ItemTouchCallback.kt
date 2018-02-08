package com.example.shinelon.lianqin.helper

import android.graphics.Canvas
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import com.example.shinelon.lianqin.NoteActivity
import com.example.shinelon.lianqin.R

/**
 * Created by Shinelon on 2018/2/8.
 */
class ItemTouchCallback(val listener: NoteActivity.ActionListener?): ItemTouchHelper.Callback() {

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        val s = viewHolder!!.adapterPosition -1
        val t = target!!.adapterPosition -1
        listener!!.dragAction(s,t)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
       listener!!.swipAction(viewHolder!!.adapterPosition-1)
    }

    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        val drag: Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swip = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

        return makeMovementFlags(drag,swip)
    }

    override fun isLongPressDragEnabled() = true

    override fun onChildDraw(c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val n = Math.abs(dX)/viewHolder!!.itemView.width
        viewHolder.itemView.scaleY =  1-n
        viewHolder.itemView.alpha = 1-n
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            viewHolder!!.itemView.setBackgroundColor(viewHolder!!.itemView!!.resources.getColor(R.color.grey))
        }
        return super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?) {
        super.clearView(recyclerView, viewHolder)
        viewHolder!!.itemView.setBackgroundColor(recyclerView!!.resources.getColor(R.color.cardview))
        viewHolder.itemView.alpha = 1F
        viewHolder.itemView.scaleY = 1F
        Log.e("clearView()","调用")
    }

}