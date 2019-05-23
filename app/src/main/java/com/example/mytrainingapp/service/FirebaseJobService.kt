package com.example.mytrainingapp.service

import com.example.mytrainingapp.data.entities.ToastEvent
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.firebase.jobdispatcher.SimpleJobService
import org.greenrobot.eventbus.EventBus
import kotlin.random.Random

class FirebaseJobService : SimpleJobService() {

    //OnRunJob is called on a background thread
    override fun onRunJob(job: JobParameters?): Int {
        //We get a random value and notify the main thread
        EventBus.getDefault().post(ToastEvent(Random.nextInt().toString()))
        return JobService.RESULT_SUCCESS
    }
}