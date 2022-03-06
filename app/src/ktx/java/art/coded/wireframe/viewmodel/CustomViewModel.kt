package art.coded.wireframe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

class CustomViewModel : ViewModel() {
    private val mText = MutableLiveData<String?>()
    val text: LiveData<String?>
        get() = mText

    companion object {
        val LOG_TAG = CustomViewModel::class.java.simpleName
    }

    init {
        mText.value = "This is a custom view"
    }
}