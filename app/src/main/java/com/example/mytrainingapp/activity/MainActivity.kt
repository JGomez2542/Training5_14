package com.example.mytrainingapp.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrainingapp.R
import com.example.mytrainingapp.adapters.RecyclerTypesAdapter
import com.example.mytrainingapp.common.PERMISSIONS_REQUEST_CODE
import com.example.mytrainingapp.data.repository.Repository
import com.example.mytrainingapp.data.repository.RepositoryImpl
import com.example.mytrainingapp.factories.MainViewModelFactory
import com.example.mytrainingapp.data.entities.Entities
import com.example.mytrainingapp.managers.RuntimePermissionsManager
import com.example.mytrainingapp.utils.snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecyclerTypesAdapter.OnClickListener {

    lateinit var mainViewModel: MainViewModel
    lateinit var factory: MainViewModelFactory
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener {
            val intent = Intent(this, ThreadingActivity::class.java)
            startActivity(intent)
        }

        repository = RepositoryImpl(applicationContext)
        factory = MainViewModelFactory(repository)
        mainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        mainViewModel.getData()
        setUpObservers()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    snackbar("Fine Location Permission Granted", "Close", coordinatorLayout)
                }
            }
        }
    }

    override fun onItemClicked(item: Entities) {
        val message = mainViewModel.getMessage(item)
        snackbar(message, "Close", coordinatorLayout)
    }

    private fun setUpObservers() {
        mainViewModel.reyclerTypesLiveData.observe(this, Observer { list ->
            list?.let {
                val myAdapter = RecyclerTypesAdapter(it, this@MainActivity)
                recyclerView.apply {

                }
                recyclerView.apply {
                    addItemDecoration(DividerItemDecoration(this@MainActivity, RecyclerView.VERTICAL))
                    adapter = myAdapter
                    layoutManager = LinearLayoutManager(this@MainActivity)
                }
            }
        }
        )

        RuntimePermissionsManager.getMessageLiveData().observe(this, Observer {
            AlertDialog.Builder(this)
                .setTitle("Permission Request")
                .setMessage(it)
                .setPositiveButton("Ok") { dialog, _ ->
                    dialog.cancel()
                    RuntimePermissionsManager.requestPermissions(this)
                }
                .show()
        })

        RuntimePermissionsManager.checkPermissions(this).observe(this, Observer { hasPermission ->
            hasPermission?.let {
                snackbar("We have location permission", "Close", coordinatorLayout)
            }
        })
    }
}
