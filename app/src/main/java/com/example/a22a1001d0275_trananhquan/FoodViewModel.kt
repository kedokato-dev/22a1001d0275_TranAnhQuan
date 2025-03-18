import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a22a1001d0275_trananhquan.Database.FoodDao
import com.example.a22a1001d0275_trananhquan.Model.FoodItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FoodViewModel(private val foodDao: FoodDao) : ViewModel() {

    private val _foodItems = MutableStateFlow<List<FoodItem>>(emptyList())
    val foodItems: StateFlow<List<FoodItem>> = _foodItems // Expose StateFlow

    init {
        loadFoodItems()
    }

    private fun loadFoodItems() {
        viewModelScope.launch(Dispatchers.IO) { // Chạy trên luồng IO
            val items = foodDao.getAllFoodItems()
            _foodItems.value = items // Cập nhật giá trị của StateFlow
        }
    }

    fun addFoodItem(food: FoodItem) {
        viewModelScope.launch(Dispatchers.IO) {
            foodDao.insertFoodItem(food) // Thêm vào database
            val updatedList = foodDao.getAllFoodItems() // Lấy danh sách mới
            _foodItems.value = updatedList // Cập nhật StateFlow
        }
    }

    fun updateFood(food: FoodItem) {
        viewModelScope.launch(Dispatchers.IO) {
            foodDao.insertFoodItem(food) // Dùng insert với `OnConflictStrategy.REPLACE` để cập nhật
            _foodItems.value = foodDao.getAllFoodItems()
        }
    }

}
