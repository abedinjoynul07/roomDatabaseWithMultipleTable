package com.shokal.roomdatabasewithmultipletable.models

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "products")
@Parcelize
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val price: Float,
    val quantity: Int,
    val categoryName: String,
    val productImage: Bitmap
) : Parcelable
