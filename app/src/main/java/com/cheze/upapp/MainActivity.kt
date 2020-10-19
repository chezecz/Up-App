package com.cheze.upapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.cheze.upapp.adapter.ItemAdapter
import com.cheze.upapp.data.DataSource
import com.cheze.upapp.service.VolleyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        VolleyService.initialize(this)
        val context = applicationContext
        job = Job()
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val accountRequest = DataSource(this)
        launch {
            val accounts = accountRequest.loadAccounts()
            val accountsObject = accountRequest.convertJsonToObject(accounts)
            recyclerView.adapter = ItemAdapter(context, accountsObject)
            recyclerView.setHasFixedSize(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}