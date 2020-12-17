package realchenzhi.androidsample.ui.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.InputType
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import realchenzhi.androidlib.ext.toDimensionValue
import realchenzhi.androidlib.ext.toPx
import realchenzhi.androidsample.R

/**
 * @author          chenyuxuan
 * @date            12/14/20
 * @description     高度封装的ItemText，统一样式
 */
class ItemEditText(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {

    private var isRequired = false
    private var preFixText = ""
    private var requireTag = "*"
    private val preFixTextColor = Color.parseColor("#333333")
    private val preFixRequiredColor = Color.RED
    private val preFixPaint = Paint()
    private var textPaddingLeft = 0f
    private var preFixVerticalOffset = 0f
    private val preFixFontMetrics: Paint.FontMetrics by lazy {
        Paint.FontMetrics()
    }

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.ItemEditText)
        isRequired = typeArray.getBoolean(R.styleable.ItemEditText_itemEditIsRequired, false)
        preFixText = typeArray.getString(R.styleable.ItemEditText_itemEditPre) ?: ""
        typeArray.recycle()

        preFixPaint.textSize = context.resources.getDimension(R.dimen.sp16)
        gravity = Gravity.CENTER_VERTICAL or Gravity.END
        textPaddingLeft = if (preFixText.isEmpty()) {
            0f
        } else {
            preFixPaint.measureText(preFixText) + 10.toPx()
        }
        setPadding(textPaddingLeft.toInt(), 0, 0, 0)

        background = null
        minHeight = 50.toPx().toInt()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //绘制preFix文字
        if (preFixText.isNotEmpty()) {
            preFixPaint.color = preFixTextColor
            preFixPaint.getFontMetrics(preFixFontMetrics)
            preFixVerticalOffset = (preFixFontMetrics.ascent + preFixFontMetrics.descent) / 2
            canvas.drawText(
                preFixText,
                0f,
                height / 2f - preFixVerticalOffset,
                preFixPaint
            )

            if (isRequired) {
                //绘制星号
                val requiredTagX = preFixPaint.measureText(preFixText)
                preFixPaint.color = preFixRequiredColor
                canvas.drawText(
                    requireTag,
                    requiredTagX,
                    height / 2f - preFixVerticalOffset,
                    preFixPaint
                )
            }
        }
    }

}