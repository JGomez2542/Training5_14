package com.example.mytrainingapp.threads

import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.mytrainingapp.common.MSG_KEY
import com.example.mytrainingapp.data.entities.MessageEvent
import com.example.mytrainingapp.managers.TaskManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MyRunnable(val handler: Handler, val sequence: Int) : Runnable {

    override fun run() {
        EventBus.getDefault().register(this)
        TaskManager.startTask(this::class.java.simpleName, sequence) { sendMessage(it)}
    }

    private fun sendMessage(int: Int) {
        val message = handler.obtainMessage()
        val bundle = Bundle()
        bundle.putInt(MSG_KEY, int)
        message.data = bundle
        handler.sendMessage(message)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageReceived(message: MessageEvent) {
        Log.d(MyRunnable::class.java.simpleName, "onMessageReceived: ${message.data} " +
                "on ${Thread.currentThread().name}")
    }

}