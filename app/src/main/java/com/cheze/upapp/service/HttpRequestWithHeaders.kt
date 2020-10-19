package com.cheze.upapp.service

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class RequestWithHeaders(
    url: String,
    continuation: Continuation<JSONObject>,
    secretValue: String,
) : JsonObjectRequest(
    Method.GET, url, null,
    Response.Listener {
        continuation.resume(it)
    },
    Response.ErrorListener {
        continuation.resumeWithException(Exception(it.cause))
    },
) {

    private val secret = secretValue

    override fun getHeaders() = mapOf(
        "Content-Type" to "application/json",
        "Authorization" to secret,
    )
}