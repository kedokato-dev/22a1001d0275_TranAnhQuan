package com.example.a22a1001d0275_trananhquan.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.a22a1001d0275_trananhquan.Model.FoodItem

@Dao
interface FoodDao {
    @Query("SELECT * FROM food_items")
    suspend fun getAllFoodItems(): List<FoodItem>


    @Insert
    suspend fun insertFoodItem(foodItem: FoodItem)
}