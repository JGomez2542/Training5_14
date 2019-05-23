package com.example.mytrainingapp.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mytrainingapp.R
import com.example.mytrainingapp.common.ACTION_BAZ
import com.example.mytrainingapp.common.ACTION_FOO
import com.example.mytrainingapp.service.MyBoundService
import com.example.mytrainingapp.service.MyIntentService
import com.example.mytrainingapp.service.MyNormalService
import com.example.mytrainingapp.utils.toast
import kotlinx.android.synthetic.main.activity_service.*

class ServiceActivity : AppCompatActivity(), ServiceConnection {

    lateinit var myBoundService: MyBoundService
    var isBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

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
}
