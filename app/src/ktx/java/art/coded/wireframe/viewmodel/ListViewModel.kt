package art.coded.wireframe.viewmodel

import androidx.lifecycle.*
import art.coded.wireframe.model.ElementRepository
import art.coded.wireframe.model.entity.Element
import kotlinx.coroutines.launch

private val LOG_TAG = ListViewModel::class.java.simpleName

class ListViewModel(private val repository: ElementRepository) : ViewModel() {

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

    fun removeData(element: Element) {
        actionPending.postValue(true)
        viewModelScope.launch {
            repository.delete(element)
        }
        actionPending.postValue(false)
    }
}

class ListViewModelFactory(private val repository: ElementRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListViewModel(repository) as T
    }
}