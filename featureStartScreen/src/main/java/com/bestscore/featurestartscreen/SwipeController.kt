package com.bestscore.featurestartscreen

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView


class SwipeController() :
    ItemTouchHelper.SimpleCallback(0, LEFT) {

    private val background: ColorDrawable = ColorDrawable()
    private var swipeBack: Boolean = false
    private var buttonsState: ButtonsState = ButtonsState.GONE

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, LEFT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }




}