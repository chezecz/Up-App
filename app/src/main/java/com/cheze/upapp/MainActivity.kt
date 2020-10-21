package com.cheze.upapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
        job = Job()
        val accountRequest = DataSource(this)
        launch {
            val accounts = accountRequest.loadAccounts()
            val accountsObject = accountRequest.convertJsonToObject(accounts)
            val recyclerFragment = BankAccountFragment.newInstance(accountsObject)
            addFragmentToActivity(recyclerFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun addFragmentToActivity(fragment: Fragment?){

        if (fragment == null) return
        supportFragmentManager.beginTransaction().add(R.id.main_layout, fragment).commitAllowingStateLoss()
    }
}