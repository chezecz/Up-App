package com.cheze.upapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.cheze.upapp.data.DataSource
import com.cheze.upapp.fragment.AccountListFragment
import com.cheze.upapp.fragment.TransactionListFragment
import com.cheze.upapp.helper.Converter
import com.cheze.upapp.service.VolleyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job
    private val converter = Converter()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        VolleyService.initialize(this)
        job = Job()
        val accountRequest = DataSource(this)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        launch {
            val accounts = accountRequest.loadAccounts()
            val accountsObject = converter.convertJsonToObject(accounts)
            val recyclerFragment = AccountListFragment.newInstance(accountsObject)
            getTransactions("b8fc7826-c7f7-4f44-a105-d24c0f3b87ce")
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

    private fun getTransactions(accountId: String) {
        val transactionRequest = DataSource(this)
        launch {
            val transactions = transactionRequest.loadTransactions(accountId)
            val transactionObject = converter.convertJsonToObject(transactions)
            val transactionFragment = TransactionListFragment.newInstance(transactionObject)
            Log.d("LISTTRANSACTIONS", transactionObject.toString())
        }
    }
}