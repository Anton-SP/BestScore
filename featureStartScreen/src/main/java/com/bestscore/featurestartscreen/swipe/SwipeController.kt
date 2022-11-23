package com.bestscore.featurestartscreen.swipe

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.RecyclerView
import com.bestscore.featurestartscreen.ButtonsState


class SwipeController() :
    ItemTouchHelper.Callback() {

    private var swipeBack: Boolean = false
    private var buttonsState: ButtonsState = ButtonsState.GONE
    private val buttonWidth = 200f
    private val buttonInstance: RectF? = null
    private var currentItemViewHolder: RecyclerView.ViewHolder? = null
    private var buttonsActions: SwipeControllerActions? = null

    constructor(buttonsActions: SwipeControllerActions?) : this() {
        this.buttonsActions = buttonsActions
    }

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

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        if (swipeBack) {
            swipeBack = buttonsState != ButtonsState.GONE
            return 0
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean

    ) {
        if (actionState == ACTION_STATE_SWIPE) {
            if (buttonsState != ButtonsState.GONE) {
                var newDX = 0f
                if (buttonsState == ButtonsState.RIGHT_VISIBLE) newDX = Math.min(dX, -buttonWidth)
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    newDX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            } else {
                setTouchListener(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }
        if (buttonsState == ButtonsState.GONE) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
        currentItemViewHolder = viewHolder
    }

    private fun setTouchListener(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        recyclerView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                swipeBack =
                    event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP
                if (swipeBack) {
                    if (dX < -buttonWidth) buttonsState = ButtonsState.RIGHT_VISIBLE

                    if (buttonsState != ButtonsState.GONE) {
                        setTouchDownListener(
                            c,
                            recyclerView,
                            viewHolder,
                            dX,
                            dY,
                            actionState,
                            isCurrentlyActive
                        )
                        setItemsClickable(recyclerView, false)
                    }
                }
                return false
            }
        })

    }

    private fun setTouchDownListener(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        recyclerView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_DOWN) {
                    setTouchUpListener(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
                return false
            }
        })
    }

    private fun setTouchUpListener(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        recyclerView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_UP) {
                    this@SwipeController.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        0F,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                    recyclerView.setOnTouchListener(object : View.OnTouchListener {
                        override fun onTouch(v: View?, event: MotionEvent): Boolean {
                            return false
                        }
                    })
                    setItemsClickable(recyclerView, true)
                    swipeBack = false

                    if (buttonsState != null && buttonInstance != null && buttonInstance.contains(
                            event.x,
                            event.y
                        )
                    ) {
                        if (buttonsState == ButtonsState.RIGHT_VISIBLE) {
                            //todo right click
                            buttonsActions?.onRightClicked(viewHolder.absoluteAdapterPosition);
                        }
                    }
                    buttonsState = ButtonsState.GONE
                    currentItemViewHolder = null
                }
                return false
            }
        })

    }

    private fun setItemsClickable(
        recyclerView: RecyclerView,
        isClickable: Boolean
    ) {
        for (i in 0 until recyclerView.childCount) {
            recyclerView.getChildAt(i).isClickable = isClickable
        }
    }

    private fun drawButtons(c: Canvas, viewHolder: RecyclerView.ViewHolder) {
        val buttonWidthWithoutPadding = buttonWidth - 20
        val corners = 16f
        val itemView = viewHolder.itemView
        val p = Paint()

       /* val leftButton = RectF(
            itemView.left.toFloat(),
            itemView.top.toFloat(), itemView.left + buttonWidthWithoutPadding,
            itemView.bottom.toFloat()
        )
        p.setColor(Color.BLUE)
        c.drawRoundRect(leftButton, corners, corners, p)
        drawText("EDIT", c, leftButton, p)*/

        val rightButton = RectF(
            itemView.right - buttonWidthWithoutPadding,
            itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat()
        )
        p.setColor(Color.RED)
        c.drawRoundRect(rightButton, corners, corners, p)
        drawText("DELETE", c, rightButton, p)
        /*var buttonInstance: RectF? = null
        if (buttonsState === ButtonsState.LEFT_VISIBLE) {
            buttonInstance = leftButton
        } else if (buttonsState === ButtonsState.RIGHT_VISIBLE) {
        }*/
    }

    private fun drawText(text: String, c: Canvas, button: RectF, p: Paint) {
        val textSize = 30f
        p.setColor(Color.WHITE)
        p.setAntiAlias(true)
        p.setTextSize(textSize)
        val textWidth: Float = p.measureText(text)
        c.drawText(text, button.centerX() - textWidth / 2, button.centerY() + textSize / 2, p)
    }

    fun onDraw(c: Canvas?) {
        if (currentItemViewHolder != null) {
            drawButtons(c!!, currentItemViewHolder!!)
        }
    }



}

/*private fun setTouchListener(
      c: Canvas,
      recyclerView: RecyclerView,
      viewHolder: RecyclerView.ViewHolder,
      dX: Float, dY: Float,
      actionState: Int, isCurrentlyActive: Boolean
  ) {
      recyclerView.setOnTouchListener { recycler, event ->
          swipeBack =
              event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP
          if (swipeBack) {
              if (dX < -buttonWidth) buttonsState = ButtonsState.RIGHT_VISIBLE

              if (buttonsState != ButtonsState.GONE) {
                  Log.d("HAPPY","HEY")
                  setTouchDownListener(
                      c,
                      recyclerView,
                      viewHolder,
                      dX,
                      dY,
                      actionState,
                      isCurrentlyActive
                  )
                  setItemsclickable(recyclerView, false)
              }
          }
          false
      }
  }
*/


/*
    private fun setTouchDownListener(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float,
        actionState: Int, isCurrentlyActive: Boolean
    ) {
        recyclerView.setOnTouchListener { recycler, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                setTouchUpListener(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
            false
        }
    }*/


/*private fun setTouchUpListener(
    c: Canvas,
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder,
    dX: Float,
    dY: Float,
    actionState: Int,
    isCurrentlyActive: Boolean
) {
    recyclerView.setOnTouchListener { recycler, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            this.onChildDraw(
                c,
                recyclerView,
                viewHolder,
                0F,
                dY,
                actionState,
                isCurrentlyActive
            )
            recyclerView.setOnTouchListener { v, event ->
                return@setOnTouchListener false
            }
            setItemsclickable(recyclerView, true)
            swipeBack = false
            buttonsState = ButtonsState.GONE
        }
        false
    }
}*/