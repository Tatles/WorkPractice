package com.example.practice.data.models

import java.io.Serializable

data class Data(
    val countOfPages: Int,
    val data: List<Image>
) : Serializable
