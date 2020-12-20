package realchenzhi.androidsample.ui.view

import android.animation.FloatEvaluator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import realchenzhi.androidsample.ui.ext.defaultBitmap
import realchenzhi.androidsample.ui.ext.log

/**
 * @author          chenyuxuan
 * @date            12/20/20
 * @description     可以双击缩放且滑动的图片
 */
class ScaledImageView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val bitmap = defaultBitmap(resources)
    var bigScale = 0f
    var smallScale = 0f
    var fraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    var offsetX = 0f
    var offsetY = 0f
    var maxOffsetX = 0f
    var maxOffsetY = 0f

    val gestureDetector by lazy(LazyThreadSafetyMode.PUBLICATION) {
        GestureDetector(context, gestureListener)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bigScale = Math.max(width / bitmap.width.toFloat(), height / bitmap.height.toFloat()) * 5f
        smallScale = Math.min(width / bitmap.width.toFloat(), height / bitmap.height.toFloat())

        maxOffsetX = (bigScale * bitmap.width - width) * 0.5f
        maxOffsetY = (bigScale * bitmap.height - height) * 0.5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.translate(offsetX * fraction, offsetY * fraction)
        canvas.scale(
            smallScale + (bigScale - smallScale) * fraction,
            smallScale + (bigScale - smallScale) * fraction,
            width / 2f,
            height / 2f
        )
        canvas.drawBitmap(
            bitmap,
            width / 2f - bitmap.width / 2f,
            height / 2f - bitmap.height / 2f,
            paint
        )
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        var big = false

        override fun onDoubleTap(e: MotionEvent): Boolean {
            big = !big
            if (big) {
                offsetX = -((e.x - width / 2) * (bigScale / smallScale - 1))
                offsetY = -((e.y - height / 2) * (bigScale / smallScale - 1))
                fixOffset()
                animator.start()
            } else {
                animator.reverse()
            }
            return false
        }

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        val animator by lazy(LazyThreadSafetyMode.PUBLICATION) {
            ObjectAnimator.ofObject(this@ScaledImageView, "fraction", FloatEvaluator(), 0, 1)
        }

        val scoller by lazy(LazyThreadSafetyMode.PUBLICATION) {
            OverScroller(context)
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (big) {
                offsetX -= distanceX
                offsetY -= distanceY
                fixOffset()
                invalidate()
            }
            return true
        }

        fun fixOffset() {
            if (offsetX > maxOffsetX) {
                offsetX = maxOffsetX
            } else if (offsetX < -maxOffsetX) {
                offsetX = -maxOffsetX
            }

            if (offsetY > maxOffsetY) {
                offsetY = maxOffsetY
            } else if (offsetY < -maxOffsetY) {
                offsetY = -maxOffsetY
            }
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (big) {
                scoller.fling(
                    offsetX.toInt(),
                    offsetY.toInt(),
                    velocityX.toInt(),
                    velocityY.toInt(),
                    -maxOffsetX.toInt(),
                    maxOffsetX.toInt(),
                    -maxOffsetY.toInt(),
                    maxOffsetY.toInt()
                )
                postOnAnimation(runnable)
            }
            return true
        }

        val runnable by lazy {
            Runnable {
                refreshFling()
            }
        }

        fun refreshFling() {
            if (scoller.computeScrollOffset()) {
                offsetX = scoller.currX.toFloat()
                offsetY = scoller.currY.toFloat()
                fixOffset()
                invalidate()
                postOnAnimation(runnable)
            }
        }
    }
}