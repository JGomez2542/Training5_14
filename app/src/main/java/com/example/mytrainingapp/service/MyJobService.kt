package com.example.mytrainingapp.service

import android.annotation.SuppressLint
import android.app.job.JobParameters
import android.app.job.JobService
import android.os.AsyncTask
import com.example.mytrainingapp.utils.toast
import kotlin.random.Random

class MyJobService : JobService() {

    private lateinit var myAsyncTask: AsyncTask<Unit, Unit, Int>

    //This method is invoked by the system when your job does not complete successfully
    override fun onStopJob(params: JobParameters?): Boolean {
        myAsyncTask.cancel(true)
        //Returning true lets the system know that we want to reschedule this job
        return true
    }

    @SuppressLint("StaticFieldLeak")
    override fun onStartJob(params: JobParameters?): Boolean {
        //By default, onStartJob does work directly on the main thread
        //return false if no time consuming work needs to be completed in a
        //background thread.
        myAsyncTask = object : AsyncTask<Unit, Unit, Int>() {

            override fun doInBackground(vararg params: Unit?): Int {
                //We get a random value and return it to our onPostExecute method
                return Random.nextInt()
            }

            override fun onPostExecute(result: Int?) {
                result?.let {
                    toast(it.toString())
                }
                //In the boolean we specify whether this job completed successfully and whether it should
                //be rescheduled or not.
                jobFinished(params, true)
            }
        }

        myAsyncTask.execute()
        //We return true since we're executing work in a background thread
        return true
    }
}