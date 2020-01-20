package com.example.medicalmanagement.helper.pojo

import java.io.Serializable

class NavDrawerItem : Serializable {
    var isShowNotify: Boolean = false
    var title: String? = null
    var photo: Int = 0
    var image: String? = null

    constructor(showNotify: Boolean, title: String) {
        this.isShowNotify = showNotify
        this.title = title
    }

    constructor(title: String, image: String) {
        this.title = title
        this.image = image
    }

    constructor(title: String, photo: Int) {
        this.title = title
        this.photo = photo
    }

}
