package art.coded.wireframe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

private val LOG_TAG = CustomViewModel::class.java.simpleName

class CustomViewModel : ViewModel() {

    val text = MutableLiveData<String>()

    init {
        text.value = "This is a custom view"
    }
}