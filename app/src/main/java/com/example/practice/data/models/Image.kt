package com.example.practice.data.models

import java.io.Serializable

data class Image(
    val name: String,
    val description: String,
    val new: Boolean,
    val popular: Boolean,
    val image: ImageInfo
) : Serializable