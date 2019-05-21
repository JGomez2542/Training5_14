package com.example.mytrainingapp.activity

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mytrainingapp.R
import com.example.mytrainingapp.data.entities.Entities
import com.example.mytrainingapp.data.repository.Repository
import com.example.mytrainingapp.data.repository.RepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class ThreadingActivity : AppCompatActivity(), CoroutineScope {

    lateinit var job: Job
    lateinit var repository: Repository
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_threading)
        job = Job()
        repository = RepositoryImpl(this)
        repository.getDataFromDb().observe(this@ThreadingActivity, Observer { list ->
            Log.d(this@ThreadingActivity.javaClass.simpleName, "Size ${list?.size}")
            list?.forEach {
                when (it) {
                    is Entities.Animal -> Log.d(
                        this@ThreadingActivity.javaClass.simpleName,
                        "This is an Animal ${it.name}"
                    )
                    is Entities.Planet -> Log.d(
                        this@ThreadingActivity.javaClass.simpleName,
                        "This is a Planet ${it.planetName}"
                    )
                    is Entities.Person -> Log.d(
                        this@ThreadingActivity.javaClass.simpleName,
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

    fun getData(view: View) {
        repository.getDataFromDb()
    }
}
