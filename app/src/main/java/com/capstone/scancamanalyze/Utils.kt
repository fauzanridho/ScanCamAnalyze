package com.capstone.scancamanalyze

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())

fun getFileNameFromUri(uri: Uri, context: Context): String? {
    uri
    val cursor = context.contentResolver.query(
        uri, arrayOf(MediaStore.Images.Media.DISPLAY_NAME),
        null, null, null
    )
    cursor?.use {
        if (it.moveToFirst()) {
            val nameIndex = it.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            return it.getString(nameIndex)
        }
    }
    return null
}

fun uriToFile(imageUri: Uri, context: Context): File {
    val fileName = getFileNameFromUri(imageUri, context)
        ?: throw IOException("File name not found")

    // Gunakan nama file asli tanpa format tambahan
    val myFile = File(context.externalCacheDir, fileName)
    val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
    val outputStream = FileOutputStream(myFile)
    val buffer = ByteArray(1024)
    var length: Int
    while (inputStream.read(buffer).also { length = it } > 0) {
        outputStream.write(buffer, 0, length)
    }
    outputStream.close()
    inputStream.close()
    return myFile
}