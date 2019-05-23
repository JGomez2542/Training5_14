package com.example.mytrainingapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyStaticReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        Log.d(MyStaticReceiver::class.java.simpleName, "onReceive: ")
    }
}