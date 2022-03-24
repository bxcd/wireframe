package art.coded.wireframe.viewmodel

import androidx.lifecycle.*
import art.coded.wireframe.model.ElementRepository
import art.coded.wireframe.model.entity.Element
import kotlinx.coroutines.launch

private val LOG_TAG = DetailViewModel::class.java.simpleName

class DetailViewModel(private val repository: ElementRepository) : ViewModel() {

    val actionPending = MutableLiveData<Boolean>()

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

class DetailViewModelFactory(private val repository: ElementRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(repository) as T
    }
}