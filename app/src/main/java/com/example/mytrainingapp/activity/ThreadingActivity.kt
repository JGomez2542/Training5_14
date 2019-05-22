package com.example.mytrainingapp.activity

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import com.example.mytrainingapp.R
import com.example.mytrainingapp.common.MSG_KEY
import com.example.mytrainingapp.data.entities.Entities
import com.example.mytrainingapp.data.repository.Repository
import com.example.mytrainingapp.data.repository.RepositoryImpl
import com.example.mytrainingapp.threads.MyRunnable
import com.example.mytrainingapp.threads.MyThread
import com.example.mytrainingapp.utils.toast
import kotlinx.android.synthetic.main.activity_threading.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class ThreadingActivity : AppCompatActivity(), CoroutineScope, Handler.Callback, View.OnClickListener {

    lateinit var job: Job
    lateinit var repository: Repository
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_threading)

        btnThread.setOnClickListener(this)
        btnRunnable.setOnClickListener(this)

        job = Job()
        repository = RepositoryImpl(this)
        repository.getDataFromDb().observe(this, Observer { list ->
            list?.forEach {
                when (it) {
                    is Entities.Animal -> Log.d(
                        javaClass.simpleName,
                        "This is an Animal ${it.name}"
                    )
                    is Entities.Planet -> Log.d(
                        javaClass.simpleName,
                        "This is a Planet ${it.planetName}"
                    )
                    is Entities.Person -> Log.d(
                        javaClass.simpleName,
                        "This is a Person ${it.name}"
                    )
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun handleMessage(msg: Message?): Boolean {
        toast(msg?.data?.getInt(MSG_KEY).toString())
        return false
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnThread -> MyThread(10).start()
            R.id.btnRunnable -> Thread(MyRunnable(Handler(this), 10)).start()
        }
    }

    fun getData(view: View) {
        repository.getDataFromDb()
    }
}
