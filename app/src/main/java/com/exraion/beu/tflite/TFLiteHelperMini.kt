package com.exraion.beu.tflite

import android.app.Activity
import android.graphics.Bitmap
import com.exraion.beu.ml.BeuMiniV2
import org.tensorflow.lite.support.image.TensorImage

fun Bitmap.predict(activity: Activity) {
    val model = BeuMiniV2.newInstance(activity)

    val newBitmap = copy(Bitmap.Config.ARGB_8888, true)
    val tfImage = TensorImage.fromBitmap(newBitmap)

    val outputs = model.process(tfImage.tensorBuffer)
        .outputFeature0AsTensorBuffer


}
