package com.example.mytrainingapp.managers

import android.util.Log

object TaskManager {

    fun startTask(className: String, sequence: Int, callback: (Int) -> Unit) {
        (1..sequence).forEach {
            Thread.sleep(500)
            Log.d(
                TaskManager::class.java.simpleName,
                "$className task $it running on ${Thread.currentThread().name}"
            )
            callback.invoke(it)
        }
    }
}