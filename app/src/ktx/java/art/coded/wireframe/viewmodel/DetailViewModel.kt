package art.coded.wireframe.viewmodel

import androidx.lifecycle.LiveData
import android.content.Context
import art.coded.wireframe.model.ElementRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import art.coded.wireframe.model.entity.Element
import kotlinx.coroutines.launch

private val LOG_TAG = DetailViewModel::class.java.simpleName

class DetailViewModel : ViewModel() {
    var data : LiveData<List<Element>>? = null
        private set
    private var mRepository: ElementRepository? = null
    fun loadData(context: Context) {
        viewModelScope.launch {
            mRepository = ElementRepository(context)
            data = mRepository!!.allElements
        }
    }

    fun addData(element: Element) {
        viewModelScope.launch {
            mRepository!!.insert(element)
        }
    }

    fun removeAllData() {
        viewModelScope.launch {
            mRepository!!.deleteAll()
        }
    }
}