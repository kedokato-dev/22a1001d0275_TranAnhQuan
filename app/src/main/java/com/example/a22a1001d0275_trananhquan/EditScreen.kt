package com.example.a22a1001d0275_trananhquan

import FoodViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.a22a1001d0275_trananhquan.Model.FoodItem

@Composable
fun EditFoodScreen(foodItem: FoodItem, viewModel: FoodViewModel, onNavigateBack: () -> Unit) {
    var name by remember { mutableStateOf(foodItem.name) }
    var price by remember { mutableStateOf(foodItem.price.toString()) }
    var imageUrl by remember { mutableStateOf(foodItem.imageUrl) }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = name, onValueChange = { name = it }, label = { Text("Tên món ăn") })
        TextField(value = price, onValueChange = { price = it }, label = { Text("Giá") })
        TextField(value = imageUrl, onValueChange = { imageUrl = it }, label = { Text("URL hình ảnh") })

        Button(
            onClick = {
                val newFood = foodItem.copy(name = name, price = price.toInt(), imageUrl = imageUrl)
                if (foodItem.id == 0) {  // Nếu ID là 0, đây là món ăn mới
                    viewModel.addFoodItem(newFood)
                } else {
                    viewModel.updateFood(newFood)
                }
                onNavigateBack()
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(if (foodItem.id == 0) "Thêm món ăn" else "Cập nhật")
        }
    }
}

