package com.example.mytrainingapp.service.workers

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mytrainingapp.common.WORKER_RANDOM_TOAST
import kotlin.random.Random

/**
 * This worker will be used to execute the work relating to generating a random integer
 */
class RandomWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

    //Do work runs on a worker thread
    override fun doWork(): Result {
        //We create a data object with our random integer
        val outputData = Data.Builder()
            .putString(WORKER_RANDOM_TOAST, Random.nextInt().toString())
            .build()
        //Indicates success or failure for this specific piece of work. OutputData is sent to our ToastWorker
        return Result.success(outputData)
    }
}