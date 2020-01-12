package com.hfad.bitsandpizzas

import android.app.Activity
import android.os.Bundle

class OrderActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        actionBar!!.setDisplayHomeAsUpEnabled(true)

    }
}
