package com.example.mytrainingapp.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyNormalService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d(
            MyNormalService::class.java.simpleName, "onCreate: $this Thread: " +
                    "${Thread.currentThread().name}"
        )

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(
            MyNormalService::class.java.simpleName, "onStartCommand: ${intent?.getStringExtra("key")} " +
                    "Thread: ${Thread.currentThread().name}"
        )
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(MyNormalService::class.java.simpleName, "onBind: $this Thread: " +
                "${Thread.currentThread().name}")
        TODO("Return the communication channel to the service.")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(
            MyNormalService::class.java.simpleName, "onDestroy: $this Thread: " +
                    "${Thread.currentThread().name}"
        )
    }
}