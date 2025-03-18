package com.example.a22a1001d0275_trananhquan

import FoodViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.example.a22a1001d0275_trananhquan.Database.FoodDatabase
import com.example.a22a1001d0275_trananhquan.Model.FoodItem

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: FoodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Lấy database instance
        val database = FoodDatabase.getDatabase(this)
        val foodDao = database.foodDao()

        // Khởi tạo ViewModel với Factory
        viewModel = ViewModelProvider(this, FoodViewModelFactory(foodDao))[FoodViewModel::class.java]

        setContent {
            FoodListScreen(viewModel)

            if (savedInstanceState == null) {
                initializeSampleData()
            }
        }
    }

    private fun initializeSampleData() {
        viewModel.addFoodItem(FoodItem(name = "Gỏi gà", price = 120000, imageUrl = "https://dienmayhoki.com/wp-content/uploads/2023/10/cach-lam-ga-quay-2.jpg"))
        viewModel.addFoodItem(FoodItem(name = "Bò lúc lắc", price = 150000, imageUrl = "https://dienmayhoki.com/wp-content/uploads/2023/10/cach-lam-ga-quay-2.jpg"))
        viewModel.addFoodItem(FoodItem(name = "Tôm hùm", price = 100000, imageUrl = "https://dienmayhoki.com/wp-content/uploads/2023/10/cach-lam-ga-quay-2.jpg"))
        viewModel.addFoodItem(FoodItem(name = "Sụp cua", price = 80000, imageUrl = "https://dienmayhoki.com/wp-content/uploads/2023/10/cach-lam-ga-quay-2.jpg"))
        viewModel.addFoodItem(FoodItem(name = "Lẩu hải sản", price = 200000, imageUrl = "https://dienmayhoki.com/wp-content/uploads/2023/10/cach-lam-ga-quay-2.jpg"))
    }
}

@Composable
fun FoodListScreen(viewModel: FoodViewModel) {
    val foodItems by viewModel.foodItems.collectAsState()
    var selectedFood by remember { mutableStateOf<FoodItem?>(null) }
    var isAdding by remember { mutableStateOf(false) }

    if (isAdding) {
        EditFoodScreen(foodItem = FoodItem(name = "", price = 0, imageUrl = ""), viewModel = viewModel) {
            isAdding = false
        }
    } else if (selectedFood != null) {
        EditFoodScreen(selectedFood!!, viewModel) { selectedFood = null }
    } else {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { isAdding = true }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Thêm món ăn")
                }
            }
        ) { paddingValues ->
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(foodItems, key = { it.id }) { foodItem ->
                    FoodItemRow(foodItem) { selectedFood = it }
                }
            }
        }
    }
}



@Composable
fun FoodItemRow(foodItem: FoodItem, onEditClick: (FoodItem) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AsyncImage(
            model = foodItem.imageUrl,
            contentDescription = foodItem.name,
            modifier = Modifier.size(80.dp).padding(end = 8.dp)
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(text = foodItem.name)
            Text(text = "Giá: ${foodItem.price} đồng")
        }

        Button(onClick = { onEditClick(foodItem) }) {
            Text("Sửa")
        }
    }
}

