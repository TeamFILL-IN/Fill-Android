package com.teamfillin.fillin.core.content

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> Call<T>.receive(
    onSuccess: (T) -> Unit,
    onError: ((stateCode: Int) -> Unit)? = null
) {
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                onSuccess.invoke(response.body() ?: return)
            } else {
                onError?.invoke(response.code())
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            Log.d("NetworkTest", "error: $t")
        }
    })
}