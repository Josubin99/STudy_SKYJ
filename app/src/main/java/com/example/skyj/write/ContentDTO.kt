package com.example.skyj.write

import android.icu.text.CaseMap
import android.net.Uri

data class ContentDTO   (var text:String? = null,
                         var title:String? = null,
                         var imageUrl:String? = null,
                         var profile:String? = null,
                         var timestamp:Long? = null,
                         var favoriteCount:String? = null,
                         var favorites:Map<String,Boolean> = HashMap()) {
    data class Comment(var commentUser:String? = null,
                       var commentProfile:String? = null,
                       var commentEmail:String? = null,
                       var commentTimestamp:Long? = null,
                       var commentText:String? = null)

}