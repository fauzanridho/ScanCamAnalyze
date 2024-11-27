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
    var threshold: Float = 0.1f,
    var maxResults: Int = 3,
    val context: Context,
    val classifierListener: ClassifierListener?
) {
    private var model: SkinCamAnalyzeModel? = null

    init {
        setupModel()
    }

    private fun setupModel() {
        try {
            model = SkinCamAnalyzeModel.newInstance(context)
        } catch (e: IOException) {
            e.printStackTrace()
            classifierListener?.onError("Failed to load model: ${e.localizedMessage}")
        }
    }

    fun classifyStaticImage(imageUri: Uri) {
        val contentResolver = context.contentResolver

        // Memuat gambar dari URI
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        }

        // Pastikan bitmap dalam konfigurasi yang dapat diakses
        val accessibleBitmap = if (bitmap.config == Bitmap.Config.HARDWARE) {
            bitmap.copy(Bitmap.Config.ARGB_8888, true)
        } else {
            bitmap
        }

        // Resize gambar ke dimensi yang sesuai (224x224)
        val resizedBitmap = Bitmap.createScaledBitmap(accessibleBitmap, 224, 224, true)

        // Log untuk memverifikasi hasil gambar yang di-resize
        Log.d("ImageClassifier", "Resized Bitmap: ${resizedBitmap.width}x${resizedBitmap.height}")

        // Konversi gambar menjadi buffer untuk input model
        val inputFeature = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        inputFeature.loadBuffer(convertBitmapToByteBuffer(resizedBitmap))

        // Log untuk memverifikasi input tensor
        Log.d("ImageClassifier", "Input Tensor: ${inputFeature.buffer.asFloatBuffer()}")

        // Menjalankan inferensi
        val outputs = model?.process(inputFeature)
        val outputBuffer = outputs?.outputFeature0AsTensorBuffer

        // Log untuk memeriksa hasil output dari model
        Log.d("ImageClassifier", "Output Tensor: ${outputBuffer?.floatArray?.joinToString(", ")}")


        // Menafsirkan hasil
        outputBuffer?.let {
            val results = parseResults(it)
            classifierListener?.onResults(results)
        }
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * 224 * 224 * 3)
        byteBuffer.order(ByteOrder.nativeOrder())
        val pixels = IntArray(224 * 224)
        bitmap.getPixels(pixels, 0, 224, 0, 0, 224, 224)
        for (pixel in pixels) {
            val r = (pixel shr 16 and 0xFF) / 255.0f
            val g = (pixel shr 8 and 0xFF) / 255.0f
            val b = (pixel and 0xFF) / 255.0f
            byteBuffer.putFloat(r)
            byteBuffer.putFloat(g)
            byteBuffer.putFloat(b)
        }
        return byteBuffer
    }

    private fun parseResults(outputBuffer: TensorBuffer): MutableList<Classifications> {
        val labels = listOf("Wajah Mulus", "Jerawat", "Flek Hitam", "Komedo") // Sesuaikan dengan kategori model
        val outputArray = outputBuffer.floatArray
        val results = mutableListOf<Classifications>()

        outputArray.forEachIndexed { index, score ->
            if (score >= threshold) {
                results.add(Classifications(labels[index], score))
            }
        }

        // Log untuk memeriksa hasil prediksi
        Log.d("ImageClassifier", "Output Tensor: ${outputBuffer.floatArray.joinToString(", ")}")
        Log.d("ImageClassifier", "Results: ${results.joinToString(", ") { "${it.label}: ${it.score}" }}")

        return results
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

