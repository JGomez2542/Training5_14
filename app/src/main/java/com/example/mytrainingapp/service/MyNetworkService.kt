package com.example.mytrainingapp.service

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.mytrainingapp.common.*
import java.io.BufferedInputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*


class MyNetworkService : IntentService("MyNetworkService") {

    override fun onHandleIntent(intent: Intent) {
        Log.d(this::class.java.simpleName, "onHandleIntent: ")

        val url = intent.getStringExtra(URL)
        val newIntent = Intent()
        var scanner: Scanner? = null

        try {
            //Convert the string Url to the URL object
            val connectionUrl = URL(url)
            val httpURLConnection = connectionUrl.openConnection() as HttpURLConnection

            //Open connection with passed Url
            httpURLConnection.connect()

            //Get the response in the input stream
            val inputStream = BufferedInputStream(httpURLConnection.inputStream)

            //Read the response using scanner and then print
            scanner = Scanner(inputStream)
            val sb = StringBuilder()
            while (scanner.hasNext()) {
                sb.append(scanner.nextLine())
            }
            val statusCode = httpURLConnection.responseCode
            val statusMessage = httpURLConnection.responseMessage
            newIntent.action = NATIVE_RECEIVER_ACTION
            newIntent.putExtra(CODE, statusCode)
            newIntent.putExtra(MESSAGE, statusMessage)
            newIntent.putExtra(RESPONSE, sb.toString())
            sendBroadcast(newIntent)

        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            scanner?.close()
        }
    }
}