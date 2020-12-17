package realchenzhi.androidsample.ui.view

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import realchenzhi.androidlib.ext.toDimensionValue
import realchenzhi.androidlib.ext.toPx
import realchenzhi.androidsample.R
import java.lang.Exception

/**
 * @author          chenyuxuan
 * @date            12/14/20
 * @description     高度封装的ItemText，统一样式
 */
class ItemTextView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    constructor(context: Context) : this(context, null) {}

    private var isRequired = false
    private var showExpand = false
    private var showBottomLine = false
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
    private val bottomLineDrawable by lazy {
        BottomLineDrawable()
    }

    private val defaultDrawPadding = 10.toPx()

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.ItemTextView)
        isRequired = typeArray.getBoolean(R.styleable.ItemTextView_isRequired, false)
        preFixText = typeArray.getString(R.styleable.ItemTextView_prefixText) ?: ""
        showExpand = typeArray.getBoolean(R.styleable.ItemTextView_showExpand, false)
//        showBottomLine = typeArray.getBoolean(R.styleable.ItemTextView_itemTextBottomLine, false)
        typeArray.recycle()

        preFixPaint.textSize = context.resources.getDimension(R.dimen.sp16)
        gravity = Gravity.CENTER_VERTICAL or Gravity.END
        textPaddingLeft = preFixPaint.measureText(preFixText)
        setPadding(textPaddingLeft.toInt(), 0, 0, 0)

        if (showExpand) {
            compoundDrawablePadding = defaultDrawPadding.toInt()
            ResourcesCompat.getDrawable(context.resources, R.drawable.ic_arrow_to_right, null)
                ?.run {
                    setBounds(0, 0, this.minimumWidth, this.minimumHeight)
                    setCompoundDrawables(null, null, this, null)
                }
        }

        maxLines = 1
        ellipsize = TextUtils.TruncateAt.END
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

        if (showBottomLine) {

        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), 50.toPx().toInt())
    }


    class BottomLineDrawable : Drawable() {

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        override fun draw(canvas: Canvas) {
            canvas.drawLine(0f, bounds.height().toFloat(), bounds.width().toFloat(), bounds.height().toFloat(), paint)
        }

        override fun setAlpha(alpha: Int) {
            paint.alpha = alpha
        }

        override fun getOpacity(): Int {
            return PixelFormat.UNKNOWN
        }

        override fun setColorFilter(colorFilter: ColorFilter?) {
            paint.colorFilter = colorFilter
        }

    }
}