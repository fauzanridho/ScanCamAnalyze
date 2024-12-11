package com.capstone.scancamanalyze.ml

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import org.tensorflow.lite.Interpreter
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ImageClassifierHelper(
    var threshold: Float = 0.5f,
    var maxResults: Int = 3,
    val context: Context,
    val classifierListener: ClassifierListener?
) {
    private var interpreter: Interpreter? = null

    init {
        setupModel()
    }

    private fun setupModel() {
        try {
            val modelBytes = context.assets.open("yolo11n_float32.tflite").readBytes()
            val modelBuffer = ByteBuffer.allocateDirect(modelBytes.size)
            modelBuffer.order(ByteOrder.nativeOrder())
            modelBuffer.put(modelBytes)
            modelBuffer.rewind()

            val options = Interpreter.Options()
            interpreter = Interpreter(modelBuffer, options)
        } catch (e: IOException) {
            e.printStackTrace()
            classifierListener?.onError("Failed to load model: ${e.localizedMessage}")
        }
    }

    fun classifyStaticImage(imageUri: Uri) {
        try {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(context.contentResolver, imageUri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
            }

            val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 250, 250, true)
            val inputBuffer = convertBitmapToByteBuffer(resizedBitmap)

            val outputBuffer = ByteBuffer.allocateDirect(4 * maxResults)
            outputBuffer.order(ByteOrder.nativeOrder())

            interpreter?.run(inputBuffer, outputBuffer)
            outputBuffer.rewind()

            // Proses hasil inferensi
            val results = parseResults(outputBuffer)
            classifierListener?.onResults(results)
        } catch (e: Exception) {
            e.printStackTrace()
            classifierListener?.onError("Failed to classify image: ${e.localizedMessage}")
        }
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val inputSize = 250
        val buffer = ByteBuffer.allocateDirect(inputSize * inputSize * 3 * 4)
        buffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(inputSize * inputSize)
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        for (pixel in intValues) {
            val r = ((pixel shr 16) and 0xFF) / 255.0f
            val g = ((pixel shr 8) and 0xFF) / 255.0f
            val b = (pixel and 0xFF) / 255.0f

            buffer.putFloat(r)
            buffer.putFloat(g)
            buffer.putFloat(b)
        }

        buffer.rewind()
        return buffer
    }

    private fun parseResults(outputBuffer: ByteBuffer): List<Classifications> {
        val results = mutableListOf<Classifications>()
        for (i in 0 until maxResults) {
            val score = outputBuffer.float
            if (score > threshold) {
                results.add(Classifications("Label_$i", score))
            }
        }
        return results.sortedByDescending { it.score }
    }

    fun close() {
        interpreter?.close()
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(results: List<Classifications>)
    }

    data class Classifications(
        val label: String,
        val score: Float
    )
}
