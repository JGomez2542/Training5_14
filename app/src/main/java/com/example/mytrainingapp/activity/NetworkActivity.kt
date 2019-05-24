package com.example.mytrainingapp.activity

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mytrainingapp.R
import com.example.mytrainingapp.common.INTENT_SERVICE_BASE_URL
import com.example.mytrainingapp.common.NATIVE_RECEIVER_ACTION
import com.example.mytrainingapp.common.URL
import com.example.mytrainingapp.receiver.NativeReceiver
import com.example.mytrainingapp.service.MyNetworkService

class NetworkActivity : AppCompatActivity() {

    private lateinit var nativeReceiver: NativeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)
        nativeReceiver = NativeReceiver()
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter()
        intentFilter.addAction(NATIVE_RECEIVER_ACTION)
        registerReceiver(nativeReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(nativeReceiver)
    }

    fun makeCall(view: View) {
        when(view.id) {
            R.id.btnNativeHttp -> {
                val intent = Intent(this, MyNetworkService::class.java)
                intent.putExtra(URL, INTENT_SERVICE_BASE_URL)
                startService(intent)
            }
        }
    }
}
