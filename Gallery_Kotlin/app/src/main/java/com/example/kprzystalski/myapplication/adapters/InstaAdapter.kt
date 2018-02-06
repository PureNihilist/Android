package com.example.kprzystalski.myapplication.adapters

import android.content.ClipDescription
import android.content.Context
import android.graphics.drawable.Drawable
import android.media.Image
import android.provider.ContactsContract
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.kprzystalski.myapplication.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_entry.view.*


class InstaAdapter : BaseAdapter {

    var imgList = ArrayList<String>()
    var context: Context? = null

    constructor(context: Context, imgList: ArrayList<String>): super(){
        this.context = context
        this.imgList = imgList
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View{
        val imgUrl = this.imgList[position]

        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var imgView = inflator.inflate(R.layout.image_entry, null)

        Picasso.with(context).load(imgUrl).into(imgView.imgSrc)
        return imgView
    }

    override fun getItem(position: Int): Any? {
        return imgList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int{
        return imgList.size
    }
}