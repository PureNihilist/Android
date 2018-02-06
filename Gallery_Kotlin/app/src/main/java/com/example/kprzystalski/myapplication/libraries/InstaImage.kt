package com.example.kprzystalski.myapplication.libraries

import com.beust.klaxon.Json
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.JsonParser

class Insta {
     class InstaDeserializer : ResponseDeserializable<ArrayList<String>> {
          override fun deserialize(content: String): ArrayList<String> {
               var imageUrls = ArrayList<String>()
               //println(content)
               var json = (JsonParser().parse(content))
               var mediaList = json.asJsonObject.get("user").asJsonObject.get("media").asJsonObject.get("nodes").asJsonArray
            //  println(mediaList.toString())
               var captionList = ArrayList<String>()
               for (media in mediaList) {
                   imageUrls.add(media.asJsonObject.get("thumbnail_src").asString)
                   captionList.add(media.asJsonObject.get("caption").asString)
               }
               for(caption in captionList) {
         //          println(caption)
               }
               return imageUrls
          }
     }
}