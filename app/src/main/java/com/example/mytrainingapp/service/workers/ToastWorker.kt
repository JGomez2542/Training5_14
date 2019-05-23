package com.example.mytrainingapp.service.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mytrainingapp.common.WORKER_RANDOM_TOAST

/**
 * If the current process is still active, then an executor will be invoked right away to execute this work, otherwise the JobScheduler, JobDispatcher, or AlarmManager
 * + Broadcast Receivers will be invoked.
 */
class ToastWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

    //Do work runs on a worker thread
    override fun doWork(): Result {
        val randomInt = inputData.getString(WORKER_RANDOM_TOAST)
        Log.d(this::class.java.simpleName, randomInt)
        return Result.success()
    }
}