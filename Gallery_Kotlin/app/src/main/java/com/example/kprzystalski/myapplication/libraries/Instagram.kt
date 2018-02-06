package com.example.kprzystalski.myapplication.libraries

import com.example.kprzystalski.myapplication.activities.GalleryActivity
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import okhttp3.Response

class Instagram(var userName: String) {

    //var userName:String = "codete_career"
    var URL:String = "https://www.instagram.com/"+userName+"/?__a=1"

    fun getImagesList(function: (ArrayList<String>)->Unit) {
        println("getImagesList")
       // var imagesList:ArrayList<String>? = null
        URL.httpGet().responseObject(Insta.InstaDeserializer()) { request, response, result ->
            val (user, err) = result
            when(result){
                is Result.Success -> {
                    if(user != null) {
                        function(user)
                    }
                }
                is Result.Failure -> {
                    print("fail")
                }
            }
        }
        println("end of getImagesList")
    }
}