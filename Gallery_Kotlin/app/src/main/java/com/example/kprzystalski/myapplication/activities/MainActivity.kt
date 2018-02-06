package com.example.kprzystalski.myapplication.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.GridView
import android.widget.TextView
import com.example.kprzystalski.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var text : EditText = input
        var userName = text.getText()
        println(userName)
        Log.d("INFO","Hello")
        button.setOnClickListener {
            Log.d("INFO","Clicked")
            val intent = GalleryActivity.newIntent(this)
            intent.putExtra("userName", userName.toString())
            startActivity(intent)
        }
    }
}
