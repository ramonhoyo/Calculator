package com.demo.andres.demo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DisplayActivityMessage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)
        val intent = getIntent()
        val message = intent.getStringExtra(MainActivity::extraMessage.toString())
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = message
    }
}
