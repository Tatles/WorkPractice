package com.example.practice.ui.scenes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.practice.R
import com.example.practice.data.models.Image
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val imageDetail = intent.extras?.getSerializable("model") as Image
        Picasso.get().load("http://gallery.dev.webant.ru/media/${imageDetail.image.name}")
            .centerCrop().fit().into(detail_image)
        detail_name.text = imageDetail.name
        detail_description.text = imageDetail.description
    }
}
