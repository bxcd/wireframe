package art.coded.wireframe.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagedList
import art.coded.wireframe.model.ElementRepository
import art.coded.wireframe.model.entity.Element
import kotlinx.coroutines.launch

private val LOG_TAG = PagingViewModel::class.java.simpleName

class PagingViewModel(private val repository: ElementRepository) : ViewModel() {

    val actionPending = MutableLiveData<Boolean>()

    fun getData(): LiveData<PagedList<Element>> {
        var elements: LiveData<PagedList<Element>>? = null
        actionPending.postValue(true)
        viewModelScope.launch {
            elements = repository.pagedList
        }
        actionPending.postValue(false)
        return elements!!
    }

    fun removeData(element: Element) {
        viewModelScope.launch {
            repository.delete(element)
        }
    }

    class PagingViewModelFactory(private val repository: ElementRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PagingViewModel(repository) as T
        }
    }
}