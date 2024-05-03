import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val token = MutableLiveData<String?>()

    fun clearData() {
        // Очистите данные по мере необходимости
        token.value = null
    }
}
