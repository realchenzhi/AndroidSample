package realchenzhi.androidsample.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import realchenzhi.androidlib.ext.toPx
import java.lang.Exception

/**
 * @author          chenyuxuan
 * @date            2020/11/23
 * @description
 */
class CircleImageView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    private val radius = 100.toPx(context)
    private val padding = 20.toPx(context)
    private val paint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.color = Color.parseColor("#aa764d")
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = ((padding + radius) * 2).toInt()

        //根据父View的策略及自己的期望尺寸决定出实际尺寸
        val measuredWidth = View.resolveSize(size, widthMeasureSpec)
        /*val measuredWidth = when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.EXACTLY -> {
                MeasureSpec.getSize(widthMeasureSpec)
            }
            MeasureSpec.AT_MOST -> {
                if (size > MeasureSpec.getSize(widthMeasureSpec)) {
                    MeasureSpec.getSize(widthMeasureSpec)
                } else {
                    size
                }
            }
            MeasureSpec.UNSPECIFIED -> size
            else -> size
        }*/
        val measuredHeight = View.resolveSize(size, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val realRadius = width.coerceAtMost(height) / 2 - padding
        canvas.drawCircle(width / 2f, height / 2f, realRadius, paint)
    }

}