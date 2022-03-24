package art.coded.wireframe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider

private val LOG_TAG = CustomViewModel::class.java.simpleName

class CustomViewModel : ViewModel() {

    val text = MutableLiveData<String>()

    init {
        text.value = "This is a custom view"
    }
}

class CustomViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CustomViewModel() as T
    }
}