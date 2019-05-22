package com.example.mytrainingapp.threads

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.mytrainingapp.data.entities.MessageEvent
import com.example.mytrainingapp.managers.TaskManager
import org.greenrobot.eventbus.EventBus

class MyThread(val sequence: Int) : Thread() {

    val handler: Handler = Handler(Looper.getMainLooper())

    override fun run() {
        Log.d("MyThread", "onRun: $sequence")

        TaskManager.startTask(this::class.java.simpleName, sequence) {
            sendMessage(it)
        }
        EventBus.getDefault().post(MessageEvent("Task Completed"))
        super.run()
    }

    fun sendMessage(int: Int) {
        handler.post {
            Log.d(
                MyThread::class.java.simpleName,
                "sendMessage: ${currentThread().name} " +
                        "task running: $int"
            )
        }
    }
}