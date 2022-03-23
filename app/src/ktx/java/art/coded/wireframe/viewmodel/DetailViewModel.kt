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
    lateinit var data : LiveData<List<Element>>
        private set
    private lateinit var mRepository: ElementRepository
    fun loadData(repository: ElementRepository) {
        viewModelScope.launch {
            mRepository = repository
            data = mRepository.allElements
        }
    }

    fun addData(element: Element) {
        viewModelScope.launch {
            mRepository.insert(element)
        }
    }

    fun removeAllData() {
        viewModelScope.launch {
            mRepository.deleteAll()
        }
    }
}