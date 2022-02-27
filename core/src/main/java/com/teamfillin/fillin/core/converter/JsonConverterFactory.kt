package com.teamfillin.fillin.core.converter

import kotlinx.serialization.Serializable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import javax.inject.Inject

class JsonConverterFactory @Inject constructor(
    private val gsonConverterFactory: GsonConverterFactory,
    @KotlinSerializer private val jsonConverterFactory: Converter.Factory
) : Converter.Factory() {
    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return jsonConverterFactory.requestBodyConverter(
            type,
            parameterAnnotations,
            methodAnnotations,
            retrofit
        )
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return jsonConverterFactory.responseBodyConverter(type, annotations, retrofit)
    }
}
