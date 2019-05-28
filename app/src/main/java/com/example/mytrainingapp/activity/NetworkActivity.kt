package com.example.mytrainingapp.activity

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mytrainingapp.R
import com.example.mytrainingapp.common.INTENT_SERVICE_BASE_URL
import com.example.mytrainingapp.common.NATIVE_RECEIVER_ACTION
import com.example.mytrainingapp.common.PERSON_BASE_URL
import com.example.mytrainingapp.common.URL
import com.example.mytrainingapp.receiver.NativeReceiver
import com.example.mytrainingapp.service.MyNetworkService
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext
import org.json.JSONException
import com.example.mytrainingapp.utils.toast
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException
import com.google.gson.Gson
import android.util.Log
import com.example.mytrainingapp.data.remote.RemoteServiceHelper
import com.example.mytrainingapp.models.RestCallPerson
import com.example.mytrainingapp.models.WeatherData
import com.example.mytrainingapp.utils.snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_network.*


class NetworkActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    private lateinit var nativeReceiver: NativeReceiver
    private lateinit var remoteServiceHelper: RemoteServiceHelper

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)
        nativeReceiver = NativeReceiver()
        remoteServiceHelper = RemoteServiceHelper(this)
        job = Job()
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

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    fun makeCall(view: View) {
        val okHttpClient = OkHttpClient()

        when (view.id) {
            R.id.btnNativeHttp -> {
                val intent = Intent(this, MyNetworkService::class.java)
                intent.putExtra(URL, INTENT_SERVICE_BASE_URL)
                startService(intent)
            }
            R.id.btnOkHttpSync -> {
                val syncRequest = Request.Builder()
                    .url(PERSON_BASE_URL)
                    .build()

                launch {
                    val response = okHttpClient.newCall(syncRequest).execute()
                    val jsonObject = JSONObject(response.body()?.string())
                    try {
                        withContext(Dispatchers.Main) {
                            toast(jsonObject.getString("name"))
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }

            R.id.btnOkHttpAsync -> {
                val asyncRequest = Request.Builder()
                    .url(PERSON_BASE_URL)
                    .build()
                okHttpClient.newCall(asyncRequest).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        try {
                            val gson = Gson()
                            val personJson = response.body()?.string()
                            val person = gson.fromJson(personJson, RestCallPerson::class.java)
                            runOnUiThread {
                                toast(person.toString())
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                })
            }

            R.id.btnRetrofitSync -> {
                val gson = Gson()
                launch {
                    try {
                        val response = remoteServiceHelper.getWeatherData().execute()
                        val json = response.body()?.string()
                        val data = gson.fromJson(json, WeatherData::class.java)
                        Log.d(
                            this@NetworkActivity::class.java.simpleName,
                            "City name: ${data.city.name} City Population: ${data.city.population}"
                        )
                        checkResponseOrigin(response)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            R.id.btnRetrofitAsync -> {
                val gson = Gson()
                remoteServiceHelper.getWeatherData().enqueue(object :
                    retrofit2.Callback<ResponseBody> {
                    override fun onResponse(
                        call: retrofit2.Call<ResponseBody>,
                        response: retrofit2.Response<ResponseBody>
                    ) {
                        val json = response.body()?.string()
                        val data = gson.fromJson(json, WeatherData::class.java)
                        Log.d(
                            this@NetworkActivity::class.java.simpleName,
                            "City name: ${data.city.name} City Population: ${data.city.population}"
                        )
                        checkResponseOrigin(response)
                    }

                    override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                        t.printStackTrace()
                    }
                })

            }

            R.id.btnRetrofitRxJava -> {
                remoteServiceHelper.getWeatherDataRxJava()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d(
                            this@NetworkActivity::class.java.simpleName,
                            "City name: ${it.city.name} City Population: ${it.city.population}"
                        )
                    }
                    ) { it.printStackTrace() }
            }

            R.id.btnRxJavaActivity -> {
                val intent = Intent(this, RxJavaActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun checkResponseOrigin(response: retrofit2.Response<ResponseBody>) {
        response.raw()?.cacheResponse()?.let {
            snackbar(
                message = "Response was served from cache",
                action = "Close",
                rootLayout = networkCL
            )
        }

        response.raw()?.networkResponse()?.let {
            runOnUiThread {
                toast(message = "Response was served from network/server")
            }
        }
    }
}
