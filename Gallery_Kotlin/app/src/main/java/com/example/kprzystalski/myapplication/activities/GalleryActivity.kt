package com.example.kprzystalski.myapplication.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.example.kprzystalski.myapplication.libraries.Instagram
import com.squareup.picasso.Picasso
import com.example.kprzystalski.myapplication.R.id.imageGrid
import android.graphics.Bitmap
import android.graphics.Picture
import android.media.Image
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.TextView
import com.example.kprzystalski.myapplication.R
import com.example.kprzystalski.myapplication.adapters.InstaAdapter
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.activity_main.*


class GalleryActivity : AppCompatActivity() {

    var imagesList : ArrayList<String>? = null
    var gv : GridView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        val bundle = intent.extras
        val userName = bundle.getString("userName")
       // var userName = intent.getBundleExtra("userName")
       // println(userName + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
        this.gv = imageGrid
        Instagram(userName).getImagesList({
            send(it)
        })
    }

    fun send(list : ArrayList<String>){
        println("SEND FUN")
        this.imagesList = list
        /*
        if(list != null) {
            println("list not null")
            list.forEach { image ->
                println("obrazek $image")
            }
        }*/
        var localgrid : GridView = imageGrid
        localgrid.adapter = InstaAdapter(this,list)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, GalleryActivity::class.java)
            return intent
        }
    }
}