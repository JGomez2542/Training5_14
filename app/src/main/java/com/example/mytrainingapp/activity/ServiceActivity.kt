package com.example.mytrainingapp.activity

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mytrainingapp.R
import com.example.mytrainingapp.common.ACTION_BAZ
import com.example.mytrainingapp.common.ACTION_FOO
import com.example.mytrainingapp.common.RANDOM_TOAST_JOB_ID
import com.example.mytrainingapp.common.RANDOM_TOAST_JOB_TAG
import com.example.mytrainingapp.data.entities.ToastEvent
import com.example.mytrainingapp.service.*
import com.example.mytrainingapp.service.workers.ToastWorker
import com.example.mytrainingapp.service.workers.NotificationWorker
import com.example.mytrainingapp.service.workers.RandomWorker
import com.example.mytrainingapp.utils.toast
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.firebase.jobdispatcher.Trigger
import kotlinx.android.synthetic.main.activity_service.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.TimeUnit

class ServiceActivity : AppCompatActivity(), ServiceConnection {

    lateinit var myBoundService: MyBoundService
    var isBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
        setUpOnClickListeners()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        isBound = false
        toast("Unbounded")
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as MyBoundService.MyBinder
        myBoundService = binder.getService()
        isBound = true
        toast("Bounded")
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onToastMessageEvent(event: ToastEvent) {
        toast(event.message)
    }

    private fun setUpOnClickListeners() {

        val normalIntent = Intent(applicationContext, MyNormalService::class.java).putExtra("key", "some data")
        btnStartNormal.setOnClickListener { startService(normalIntent) }
        btnStopNormal.setOnClickListener { stopService(normalIntent) }

        //Bind service
        val bindIntent = Intent(applicationContext, MyBoundService::class.java)
        btnBindService.setOnClickListener { bindService(bindIntent, this, Context.BIND_AUTO_CREATE) }
        btnAddToList.setOnClickListener { if (isBound) myBoundService.addToList(etAddToList.text.toString()) }
        btnClearList.setOnClickListener { if (isBound) myBoundService.clearList() }
        btnPrintList.setOnClickListener {
            if (isBound) {
                myBoundService.dataList.forEach {
                    Log.d(ServiceActivity::class.java.simpleName, "onCreate: $it")
                    toast(it)
                }
            }
        }
        btnUnBindService.setOnClickListener {
            if (isBound) {
                unbindService(this)
                isBound = false
            }
        }

        btnStartFoo.setOnClickListener {
            val intent = Intent(this, MyIntentService::class.java)
            intent.action = ACTION_FOO
            startService(intent)
        }

        btnStartBaz.setOnClickListener {
            val intent = Intent(this, MyIntentService::class.java)
            intent.action = ACTION_BAZ
            startService(intent)
        }

        btnStartNetworkActivity.setOnClickListener {
            val intent = Intent(this, NetworkActivity::class.java)
            startActivity(intent)
        }
    }

    fun startJobService(view: View) {
        //Access the job scheduler
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        //Get a reference to my job service implementation
        val componentName = ComponentName(this@ServiceActivity, MyJobService::class.java)

        //Build a job info to run MyJobService
        //The job id can be any integer, it just needs to be unique
        //You can add more constraints as needed when building your job
        jobScheduler.schedule(
            JobInfo.Builder(RANDOM_TOAST_JOB_ID, componentName)
                .setOverrideDeadline(TimeUnit.SECONDS.toSeconds(2))
                .build()
        )
    }

    fun startJobDispatcher(view: View) {
        val firebaseJobDispatcher = FirebaseJobDispatcher(GooglePlayDriver(this))
        firebaseJobDispatcher.mustSchedule(
            firebaseJobDispatcher.newJobBuilder()
                .setTag(RANDOM_TOAST_JOB_TAG)
                .setService(FirebaseJobService::class.java)
                .setTrigger(
                    Trigger.executionWindow(
                        0, //Can start immediately
                        1 //Wait at most one second
                    )
                )
                .build()
        )
    }

    //Use PeriodicWorkRequestBuilder for recurring tasks
    fun startWorkManager(view: View) {
        val randomWorker = OneTimeWorkRequestBuilder<RandomWorker>().build()
        val logWorker = OneTimeWorkRequestBuilder<ToastWorker>().build()
        val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>().build()
        //Here we start off by completing the work related to generating a random integer, return the random integer, then do the work
        //related to logging the integer and creating a notification for the user.
        WorkManager.getInstance()
            .beginWith(randomWorker)
            .then(listOf(logWorker, notificationWorker))
            .enqueue()
    }
}
