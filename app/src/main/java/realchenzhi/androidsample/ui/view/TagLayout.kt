package realchenzhi.androidsample.ui.view

import android.content.Context
import android.graphics.*
import android.util.ArrayMap
import android.util.AttributeSet
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.util.set
import androidx.core.view.children
import realchenzhi.androidlib.ext.toPx
import java.lang.Exception

/**
 * @author          chenyuxuan
 * @date            2020/11/23
 * @description
 */
class TagLayout(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private var usefulWidth = 0
    private var usedWidth = 0
    private var usedHeight = 0
    private var lineHeight = 0
    private var lineUsedWidth = 0

    private val childrenBounds = SparseArray<Rect>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        lineUsedWidth = 0
        usedHeight = 0
        lineHeight = 0
        usedWidth = 0

        var childMeasuredWidth = 0
        var childMeasureHeight = 0

        children.forEachIndexed { index, view ->
            measureChildWithMargins(
                view, widthMeasureSpec
                , 0, heightMeasureSpec, usedHeight
            )

            childMeasureHeight = view.measuredHeight
            childMeasuredWidth = view.measuredWidth

            if (lineUsedWidth + childMeasuredWidth > MeasureSpec.getSize(widthMeasureSpec)) {
                usedHeight += lineHeight
                lineUsedWidth = 0
                lineHeight = 0
            }

            lineHeight = lineHeight.coerceAtLeast(childMeasureHeight)

            with(childrenBounds[index] ?: Rect()) {
                left = lineUsedWidth
                top = usedHeight
                right = lineUsedWidth + childMeasuredWidth
                bottom = usedHeight + childMeasureHeight
                childrenBounds[index] = this
            }

            lineUsedWidth += childMeasuredWidth
            usedWidth = usedWidth.coerceAtLeast(lineUsedWidth)
        }

        usedHeight += lineHeight
        setMeasuredDimension(usedWidth, usedHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        children.forEachIndexed { index, view ->
            childrenBounds[index]?.run {
                view.layout(this.left, this.top, this.right, this.bottom)
            }
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

}