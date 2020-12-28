package realchenzhi.androidsample.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import realchenzhi.androidsample.ui.ext.defaultBitmap


/**
 * @author          chenyuxuan
 * @date            12/28/20
 * @description
 */
class MultiTouchView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val bitmap = defaultBitmap(resources)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var currentX = 0f
    private var currentY = 0f

    private var offsetX = 0f
    private var offsetY = 0f

    /** 当前事件手指的id */
    private var trackId = 0

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                if (!pointInBitmap(event.x, event.y)) {
                    return false
                }

                currentX = event.x
                currentY = event.y
                trackId = event.getPointerId(0)
            }
            MotionEvent.ACTION_MOVE -> {
                offsetX += event.getX(event.findPointerIndex(trackId)) - currentX
                offsetY += event.getY(event.findPointerIndex(trackId)) - currentY

                currentX = event.getX(event.findPointerIndex(trackId))
                currentY = event.getY(event.findPointerIndex(trackId))
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                if (!pointInBitmap(
                        event.getX(event.actionIndex),
                        event.getY(event.actionIndex)
                    )
                ) {
                    return false
                }

                //新的手指介入
                trackId = event.getPointerId(event.actionIndex)

                currentX = event.getX(event.findPointerIndex(trackId))
                currentY = event.getY(event.findPointerIndex(trackId))
            }
            MotionEvent.ACTION_POINTER_UP -> {
                //如果当前追踪的Pointer抬起
                if (trackId == event.getPointerId(event.actionIndex)) {
                    if (event.actionIndex != 0) {
                        currentX = event.x
                        currentY = event.y
                        trackId = event.getPointerId(0)
                    } else {
                        currentX = event.getX(1)
                        currentY = event.getY(1)
                        trackId = event.getPointerId(1)
                    }
                }
            }
            else -> {
                //nothing
            }
        }
        invalidate()
        return true
    }

    private fun pointInBitmap(x: Float, y: Float): Boolean {
        return (x in offsetX..offsetX + bitmap.width) && (y in offsetY..offsetY + bitmap.height)
    }

}