package com.example.mytrainingapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.TextView
import com.example.mytrainingapp.common.KEY_CUSTOM

class MyDynamicReceiver(val tvReceiverContent: TextView): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {

        Log.d(MyDynamicReceiver::class.java.simpleName, "onReceive: ")
        tvReceiverContent.text = intent.getStringExtra(KEY_CUSTOM)
    }

}