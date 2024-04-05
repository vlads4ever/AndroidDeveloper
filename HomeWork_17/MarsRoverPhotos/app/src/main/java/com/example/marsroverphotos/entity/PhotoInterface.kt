package com.example.marsroverphotos.entity

interface PhotoInterface {
    val id: Int
    val sol: Int
    val camera: CameraInterface
    val img_src: String
    val earth_date: String
    val rover: RoverInterface
}