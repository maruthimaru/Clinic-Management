package com.example.medicalmanagement.helper.pojo

class AdminHomeModel{
    var image: Int = 0
    lateinit var name: String

    constructor(image: Int, name: String) {
        this.image = image
        this.name = name
    }
}
