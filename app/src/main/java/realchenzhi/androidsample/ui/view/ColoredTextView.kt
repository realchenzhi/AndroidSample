package realchenzhi.androidsample.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import realchenzhi.androidlib.ext.toPx

/**
 * @author          chenyuxuan
 * @date            2020/12/10
 * @description
 */
class ColoredTextView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    companion object {
        private val textPool =
            arrayOf("大声点", "话费哈方式可点击", "分手饭撒的", "f", "党费", "啊啊啊但是防范大风大风", "大声饭撒的发点")
        private val textSizePool = intArrayOf(22, 12, 30, 27)
    }

    private val paint by lazy(LazyThreadSafetyMode.PUBLICATION) {
        Paint(Paint.ANTI_ALIAS_FLAG).also {
            it.color = Color.parseColor("#aa339292")
            it.textSize = textSizePool.random().toPx(context)
        }
    }
    private val padding = 5.toPx(context).toInt()
    private val round = 5.toPx(context)

    init {
        text = textPool.random()
        setTextColor(Color.parseColor("#000000"))
        setPadding(padding, padding, padding, padding)
        textSize = textSizePool.random().toFloat()
        gravity = Gravity.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRoundRect(
            0f,
            0f,
            right.toFloat() - left,
            bottom.toFloat() - top,
            round,
            round,
            paint
        )
    }

}