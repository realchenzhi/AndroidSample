package realchenzhi.androidsample.ui.ext

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import realchenzhi.androidsample.R

/**
 * @author          chenyuxuan
 * @date            12/20/20
 * @description
 */

fun Any.defaultBitmap(resources: Resources): Bitmap {
    return (ResourcesCompat.getDrawable(
        resources,
        R.drawable.picture,
        null
    ) as BitmapDrawable).bitmap
}

fun Any.log(msg: String) {
    Log.d("TempLog", msg)
}