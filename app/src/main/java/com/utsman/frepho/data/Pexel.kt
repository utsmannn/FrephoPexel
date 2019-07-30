package com.utsman.frepho.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pexel(val id: Long,
                 val width: Int,
                 val height: Int,
                 val url: String,
                 val photographer: String,
                 val photographer_url: String,
                 val src: Source) : Parcelable

@Parcelize
data class Source(val original: String,
                  val large2x: String,
                  val large: String,
                  val medium: String,
                  val small: String,
                  val portrait: String,
                  val landscape: String,
                  val tiny: String) : Parcelable

data class Responses(val page: Int,
                 val per_page: Int,
                 val photos: List<Pexel>)

data class Category(val query: String,
                    val url: String)