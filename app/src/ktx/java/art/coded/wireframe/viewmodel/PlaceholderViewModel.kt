package art.coded.wireframe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

class PlaceholderViewModel : ViewModel() {
    private val mText: MutableLiveData<String?>
    val text: LiveData<String?>
        get() = mText

    companion object {
        val LOG_TAG = PlaceholderViewModel::class.java.simpleName
    }

    init {
        mText = MutableLiveData()
        mText.value = "This is placeholder fragment"
    }
}