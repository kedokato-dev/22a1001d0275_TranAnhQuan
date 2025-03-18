package com.example.a22a1001d0275_trananhquan.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_items")
data class FoodItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val price: Int,
    val imageUrl: String // You can store image URLs or local resource IDs
)