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
class SquareImageView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size =
            MeasureSpec.getSize(widthMeasureSpec)
                .coerceAtLeast(MeasureSpec.getSize(heightMeasureSpec))

        setMeasuredDimension(size, size)
    }


}