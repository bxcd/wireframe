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
    private lateinit var mRepository: ElementRepository
    lateinit var data: LiveData<List<Element>>
        private set

    fun loadData(repository: ElementRepository) {
        mRepository = repository
        viewModelScope.launch {
            data = mRepository.allElements
        }
    }

    fun removeData(element: Element) {
        viewModelScope.launch {
            mRepository.delete(element)
        }
    }
}