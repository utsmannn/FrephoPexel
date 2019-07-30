package com.utsman.frepho.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.utsman.frepho.R
import com.utsman.frepho.base.loadFromCache
import com.utsman.frepho.base.loadUrl
import com.utsman.frepho.base.loadUrlCache
import com.utsman.frepho.base.logi
import com.utsman.frepho.data.Pexel
import com.utsman.frepho.module.GlideApp
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity : AppCompatActivity() {

    private val pexel by lazy { intent.getParcelableExtra("pexel") as Pexel }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        image_view_photo.loadUrlCache(pexel.src.large2x, pexel.src.medium)

        /*Glide.with(this)
                .load(pexel.src.original)
                .priority(Priority.HIGH)
                .into(image_view_photo)*/

        photographer_text_view.text = "Photographer: ${pexel.photographer}"
        logi("qqqqqqq", pexel.src.original)

    }
}