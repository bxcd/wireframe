package art.coded.wireframe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider

private val LOG_TAG = TabViewModel::class.java.simpleName

class TabViewModel : ViewModel() {

    private val index = MutableLiveData<Int>()
    val text = Transformations.map(index) { input: Int -> "Hello world from section: $input" }

    fun setIndex(index: Int) {
        this.index.value = index
    }
}

class TabViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TabViewModel() as T
    }
}