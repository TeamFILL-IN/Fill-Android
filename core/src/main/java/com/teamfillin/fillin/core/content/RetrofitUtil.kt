package com.teamfillin.fillin.core.content

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

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
            Timber.d("RetrofitUtil: Error $t")
        }
    })
}