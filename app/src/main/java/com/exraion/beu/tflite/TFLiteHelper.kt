package com.exraion.beu.tflite

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.TensorOperator
import org.tensorflow.lite.support.common.TensorProcessor
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp
import org.tensorflow.lite.support.label.TensorLabel
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.Collections


class TFLiteHelper(
    private val context: Context
) {
    
    private var imageSizeX = 0
    private var imageSizeY = 0
    
    private var labels: List<String>? = null
    private var tflite: Interpreter? = null
    
    private var inputImageBuffer: TensorImage? = null
    private var outputProbabilityBuffer: TensorBuffer? = null
    private var probabilityProcessor: TensorProcessor? = null
    
    companion object {
        private const val IMAGE_MEAN = 0.0f
        private const val IMAGE_STD = 1.0f
    
        private const val PROBABILITY_MEAN = 0.0f
        private const val PROBABILITY_STD = 255.0f
    
        private const val MODEL_PATH = "beu_mini_v2.tflite"
        private const val LABELS_PATH = "beu_labels.txt"
    }
    
    init {
        try {
            val opt = Interpreter.Options()
            tflite = loadModelFile(context as Activity)?.let { Interpreter(it, opt) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    fun classifyImage(bitmap: Bitmap?) {
        val imageTensorIndex = 0
        val imageShape = tflite!!.getInputTensor(imageTensorIndex).shape() // {1, height, width, 3}
        imageSizeY = imageShape[1]
        imageSizeX = imageShape[2]
        val imageDataType: DataType = tflite!!.getInputTensor(imageTensorIndex).dataType()
        val probabilityTensorIndex = 0
        val probabilityShape =
            tflite!!.getOutputTensor(probabilityTensorIndex).shape() // {1, NUM_CLASSES}
        val probabilityDataType: DataType =
            tflite!!.getOutputTensor(probabilityTensorIndex).dataType()
        inputImageBuffer = TensorImage(imageDataType)
        outputProbabilityBuffer =
            TensorBuffer.createFixedSize(probabilityShape, probabilityDataType)
        probabilityProcessor = TensorProcessor.Builder().add(getPostProcessNormalizeOp()).build()
        inputImageBuffer = loadImage(bitmap!!)
        tflite!!.run(inputImageBuffer!!.buffer, outputProbabilityBuffer!!.buffer.rewind())
    }
    
    private fun loadModelFile(activity: Activity): MappedByteBuffer? {
        val fileDescriptor = activity.assets.openFd(MODEL_PATH)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel: FileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }
    
    private fun loadImage(bitmap: Bitmap): TensorImage? {
        inputImageBuffer!!.load(bitmap)
        val cropSize = bitmap.width.coerceAtMost(bitmap.height)
        val imageProcessor: ImageProcessor = ImageProcessor.Builder()
            .add(ResizeWithCropOrPadOp(cropSize, cropSize))
            .add(ResizeOp(imageSizeX, imageSizeY, ResizeOp.ResizeMethod.BILINEAR))
            .add(getPreprocessNormalizeOp())
            .build()
        return imageProcessor.process(inputImageBuffer)
    }
    
    private fun getPreprocessNormalizeOp(): TensorOperator {
        return NormalizeOp(IMAGE_MEAN, IMAGE_STD)
    }
    
    private fun getPostProcessNormalizeOp(): TensorOperator {
        return NormalizeOp(PROBABILITY_MEAN, PROBABILITY_STD)
    }
    
    fun showResult(): String? {
        labels = try {
            FileUtil.loadLabels(context, LABELS_PATH)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return null
        }
        val labeledProbability =
            labels?.let {
                TensorLabel(it, probabilityProcessor!!.process(outputProbabilityBuffer))
                    .mapWithFloatValue
            }
        val maxValueInMap: Float = Collections.max(labeledProbability!!.values)
        var result: String? = null
        labeledProbability.forEach { (key, value) ->
            if (value == maxValueInMap) {
                result = key
            }
        }
        
        return result
    }
    
    
}