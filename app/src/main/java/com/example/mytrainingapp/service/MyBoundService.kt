package com.example.mytrainingapp.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyBoundService : Service() {

    val dataList = mutableListOf<String>()
    val mBinder = MyBinder()

    override fun onCreate() {
        super.onCreate()
        Log.d(MyBoundService::class.java.simpleName, "onCreate: ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(MyBoundService::class.java.simpleName, "onStartCommand: ")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(MyBoundService::class.java.simpleName, "onBind: ")
        return mBinder
    }

    override fun onDestroy() {
        Log.d(MyBoundService::class.java.simpleName, "onDestroy: ")
        super.onDestroy()
    }

    fun addToList(string: String) =  dataList.add(string)

    fun clearList() = dataList.clear()

    inner class MyBinder : Binder() {
        fun getService() = this@MyBoundService
    }
}
