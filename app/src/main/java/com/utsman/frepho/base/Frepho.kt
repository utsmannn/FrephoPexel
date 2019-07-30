package com.utsman.frepho.base

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.utsman.frepho.module.GlideApp
import com.utsman.frepho.viewmodel.CategoryViewModel
import com.utsman.frepho.viewmodel.MainViewModel
import jp.wasabeef.glide.transformations.BlurTransformation

const val HEADER = "Authorization: 563492ad6f91700001000001880a2e3eb5d6452a94a3dd050f6395a6"
const val BASE_URL = "https://api.pexels.com/"

fun Context.toast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT)

fun Fragment.toast(msg: String) =
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT)

fun logi(tag: String, msg: String) =
        Log.i(tag, msg)

fun loge(tag: String, msg: String) =
        Log.e(tag, msg)

fun String.capitalizeFirstWord() = substring(0,1).toUpperCase() + substring(1)

fun ImageView.thumBlur(urlSmall: String) =
        GlideApp.with(this.context)
                .load(urlSmall)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .transform(BlurTransformation(25))

fun ImageView.thumBlurCache(urlSmall: String) =
        GlideApp.with(this.context)
                .load(urlSmall)
                .onlyRetrieveFromCache(true)
                .centerCrop()
                .transform(BlurTransformation(25))

fun ImageView.thumBlurBitmap(urlSmall: String) =
        GlideApp.with(this.context)
                .asBitmap()
                .load(urlSmall)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .transform(BlurTransformation(25))

fun ImageView.loadUrlBlur(url: String) =
        GlideApp.with(this.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transform(BlurTransformation(15))
                .into(this)

fun ImageView.loadUrl(url: String) =
        GlideApp.with(this.context)
                .load(url)
                .into(this)

fun ImageView.loadUrl(url: String, urlBlur: String) =
        GlideApp.with(this.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .thumbnail(thumBlur(urlBlur))
                .into(this)

fun ImageView.loadUrlCache(url: String, urlBlur: String) =
        GlideApp.with(this.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .thumbnail(thumBlurCache(urlBlur))
                .into(this)

fun ImageView.loadUrl(url: String, listener: RequestListener<Drawable>) =
        GlideApp.with(this.context)
                .load(url)
                .listener(listener)
                .into(this)

fun ImageView.loadUrl(url: String, urlBlur: String, listener: RequestListener<Drawable>) =
        GlideApp.with(this.context)
                .load(url)
                .listener(listener)
                .thumbnail(thumBlur(urlBlur))
                .into(this)

fun ImageView.loadUrlBitmap(url: String, urlBlur: String, listener: RequestListener<Bitmap>) =
        GlideApp.with(this.context)
                .asBitmap()
                .load(url)
                .listener(listener)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .thumbnail(thumBlurBitmap(urlBlur))
                .into(this)

fun ImageView.loadFromCache(url: String) =
        GlideApp.with(this.context)
                .load(url)
                .onlyRetrieveFromCache(true)
                .into(this)

fun ImageView.loadUrlBitmap(url: String, listener: RequestListener<Bitmap>) =
        GlideApp.with(this.context)
                .asBitmap()
                .load(url)
                .addListener(listener)
                .into(this)

fun layoutInflater(parent: ViewGroup, layoutRes: Int): View =
        LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)

fun configPaged(size: Int): PagedList.Config = PagedList.Config.Builder()
        .setPageSize(size)
        .setInitialLoadSizeHint(size * 2)
        .setEnablePlaceholders(true)
        .build()

fun Fragment.injectMainViewModel() =
        ViewModelProviders.of(this)[MainViewModel::class.java]

fun FragmentActivity.injectMainViewModel() =
        ViewModelProviders.of(this)[MainViewModel::class.java]

fun Fragment.injectCategoryViewModel() =
        ViewModelProviders.of(this)[CategoryViewModel::class.java]

fun toVisibility(constraint: Boolean): Int {
    return if (constraint) View.VISIBLE
    else View.GONE
}

val categories = arrayOf(
        "city",
        "nature",
        "science",
        "education",
        "people",
        "feelings",
        "health",
        "places",
        "animals",
        "industry",
        "food",
        "computer",
        "sports",
        "transportation",
        "travel",
        "buildings",
        "business"
)
object Frepho {
    val CATEGORY = 0
    val ORDER = 1
    val COLOR = 2
    val EDITOR_CHOICE = 3

    object ALL {
        val CATEGORY = 4
        val COLOR = 5
    }
}