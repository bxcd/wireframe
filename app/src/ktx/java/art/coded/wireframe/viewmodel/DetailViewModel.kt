package art.coded.wireframe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import art.coded.wireframe.model.ElementRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import art.coded.wireframe.model.entity.Element
import kotlinx.coroutines.launch

private val LOG_TAG = DetailViewModel::class.java.simpleName

class DetailViewModel(private val repository: ElementRepository) : ViewModel() {

    val actionPending = MutableLiveData<Boolean>()

    fun getData(): LiveData<List<Element>> {
        var quotes: LiveData<List<Element>>? = null
        actionPending.postValue(true)
        viewModelScope.launch {
            quotes = repository.allElements
        }
        actionPending.postValue(false)
        return quotes!!
    }

    fun addData(element: Element) {
        actionPending.postValue(true)
        viewModelScope.launch {
            repository.insert(element)
        }
        actionPending.postValue(false)
    }

    fun removeAllData() {
        actionPending.postValue(true)
        viewModelScope.launch {
            repository.deleteAll()
        }
        actionPending.postValue(false)
    }
}