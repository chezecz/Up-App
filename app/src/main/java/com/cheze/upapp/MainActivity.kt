package com.cheze.upapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.cheze.upapp.adapter.ItemAdapter
import com.cheze.upapp.data.DataSource
import com.cheze.upapp.service.VolleyService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        VolleyService.initialize(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val accounts = DataSource(this).loadAccounts()
        recyclerView.adapter = ItemAdapter(this, accounts)
        recyclerView.setHasFixedSize(true)
    }
}