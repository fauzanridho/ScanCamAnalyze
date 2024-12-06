package com.capstone.scancamanalyze.ml

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ImageClassifierHelper(
    var threshold: Float = 0.5f, // Perbaiki threshold agar lebih realistis banget
    var maxResults: Int = 3,
    val context: Context,
    val classifierListener: ClassifierListener?
) {
    private var model: ModelGabungan? = null

    init {
        setupModel()
    }

    private fun setupModel() {
        try {
            model = ModelGabungan.newInstance(context)
        } catch (e: IOException) {
            e.printStackTrace()
            classifierListener?.onError("Failed to load model: ${e.localizedMessage}")
        }
    }

    fun classifyStaticImage(imageUri: Uri) {
        val contentResolver = context.contentResolver
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        }

        val accessibleBitmap = if (bitmap.config == Bitmap.Config.HARDWARE) {
            bitmap.copy(Bitmap.Config.ARGB_8888, true)
        } else {
            bitmap
        }

        // Resize gambar ke ukuran yang diharapkan model
        val resizedBitmap = Bitmap.createScaledBitmap(accessibleBitmap, 250, 250, true)

        // Konversi bitmap ke ByteBuffer
        val inputFeature = TensorBuffer.createFixedSize(intArrayOf(1, 250, 250, 3), DataType.FLOAT32)
        inputFeature.loadBuffer(convertBitmapToByteBuffer(resizedBitmap))

        // Inferensi dengan model
        val outputs = model?.process(inputFeature)
        val outputBuffer = outputs?.outputFeature0AsTensorBuffer

        // Log hasil inferensi untuk debugging
        Log.d("ImageClassifier", "Output Tensor: ${outputBuffer?.floatArray?.joinToString(", ")}")

        // Interpretasi hasil
        outputBuffer?.let {
            val results = parseResults(it)
            classifierListener?.onResults(results)
        }
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val buffer: ByteBuffer = ByteBuffer.allocateDirect(4 * 250 * 250 * 3)
        buffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(250 * 250)
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        for (pixel in intValues) {
            // Pastikan normalisasi sesuai dengan model
            val r = (pixel shr 16 and 0xFF).toFloat() / 255.0f
            val g = (pixel shr 8 and 0xFF).toFloat() / 255.0f
            val b = (pixel and 0xFF).toFloat() / 255.0f

            buffer.putFloat(r)
            buffer.putFloat(g)
            buffer.putFloat(b)
        }

        return buffer
    }

    private fun parseResults(outputBuffer: TensorBuffer): MutableList<Classifications> {
        val labels = listOf("Wajah Mulus", "Jerawat", "Flek Hitam", "Komedo")
        val outputArray = outputBuffer.floatArray
        val results = mutableListOf<Classifications>()

        outputArray.forEachIndexed { index, score ->
            if (score >= threshold) {
                val classification = Classifications(labels[index], score)
                results.add(classification)
            }
        }

        return results.sortedByDescending { it.score }.toMutableList()
    }

    fun close() {
        model?.close()
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(results: MutableList<Classifications>)
    }

    data class Classifications(
        val label: String,
        val score: Float
    )
}