package art.coded.wireframe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class TabViewModel : ViewModel() {
    private val LOG_TAG = TabViewModel::class.java.simpleName
    private val mIndex = MutableLiveData<Int>()
    val text = Transformations.map(mIndex) { input: Int -> "Hello world from section: $input" }
    fun setIndex(index: Int) {
        mIndex.value = index
    }
}