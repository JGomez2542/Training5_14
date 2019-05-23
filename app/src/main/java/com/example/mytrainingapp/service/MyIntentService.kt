package com.example.mytrainingapp.service

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.mytrainingapp.common.ACTION_BAZ
import com.example.mytrainingapp.common.ACTION_FOO
import com.example.mytrainingapp.managers.TaskManager

class MyIntentService: IntentService("MyIntentService") {

    override fun onCreate() {
        super.onCreate()
        Log.d(MyIntentService::class.java.simpleName, "onCreate: ")
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        Log.d(MyIntentService::class.java.simpleName, "onStart: ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(MyIntentService::class.java.simpleName, "onStartCommand: ")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(MyIntentService::class.java.simpleName, "onHandleIntent: ${intent?.action}")
        when(intent?.action) {
            ACTION_FOO -> {
                TaskManager.startTask("FooAction", 5) {}
            }
            ACTION_BAZ -> {
                TaskManager.startTask("BazAction", 5) {}
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(MyIntentService::class.java.simpleName, "onDestroy: ")
    }
}