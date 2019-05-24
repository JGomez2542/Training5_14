package com.example.mytrainingapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import org.json.JSONException
import org.json.JSONObject
import android.content.Intent
import android.util.Log
import com.example.mytrainingapp.common.CODE
import com.example.mytrainingapp.common.MESSAGE
import com.example.mytrainingapp.common.RESPONSE
import com.example.mytrainingapp.utils.toast


class NativeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(
            this::class.java.simpleName,
            "onReceive: ${intent.getIntExtra(CODE, 0)} \n ${intent.getStringExtra(MESSAGE)}"
        )
        val response = intent.getStringExtra(RESPONSE)
        try {
            val reader = JSONObject(response)
            val widget = reader.getJSONObject("widget")
            val text = widget.getJSONObject("text")
            val size = text.getString("style")
            context.toast(message = size)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }
}