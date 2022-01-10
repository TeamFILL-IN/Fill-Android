package com.teamfillin.fillin.core.content

import android.graphics.Bitmap
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okio.BufferedSink

/*
*.Created by Nunu Lee
* at 2022.1.10
*
* Bitmap을 RequestBody에 담아서 Multipart 서버통신을 해야할 때 사용되는 클래스입니다.
 */
class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody() {
    override fun contentType(): MediaType = "image/jpeg".toMediaType()

    override fun writeTo(sink: BufferedSink) {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 99, sink.outputStream())
    }
}