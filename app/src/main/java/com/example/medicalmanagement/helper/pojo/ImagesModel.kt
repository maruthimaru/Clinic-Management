package com.example.medicalmanagement.helper.pojo

import android.graphics.Bitmap
import java.io.Serializable

class ImagesModel : Serializable {
    lateinit var bitmap: Bitmap
    var imageList: String? = null
    var imagePath: String? = null
    var entriesItem: String? = null
    var image: Int = 0

    constructor(bitmap: Bitmap) {
        this.bitmap = bitmap
    }

    constructor(image: Int) {
        this.image = image
    }

    constructor()

    constructor(entriesItem: String, image: Int) {
        this.entriesItem = entriesItem
        this.image = image
    }
}
