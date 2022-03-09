package art.coded.wireframe.viewmodel

import androidx.lifecycle.LiveData
import android.content.Context
import art.coded.wireframe.model.ElementRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import art.coded.wireframe.model.entity.Element
import kotlinx.coroutines.launch

private val LOG_TAG = ListViewModel::class.java.simpleName

class ListViewModel : ViewModel() {
    private var mRepository: ElementRepository? = null
    var data: LiveData<List<Element>>? = null
        private set

    fun loadData(context: Context) {
        viewModelScope.launch {
            mRepository = ElementRepository(context)
            data = mRepository!!.allElements
        }
    }

    fun removeData(element: Element) {
        viewModelScope.launch {
            mRepository!!.delete(element)
        }
    }
}