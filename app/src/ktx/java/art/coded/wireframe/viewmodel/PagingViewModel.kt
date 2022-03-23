package art.coded.wireframe.viewmodel

import androidx.lifecycle.LiveData
import android.content.Context
import androidx.paging.PagedList
import art.coded.wireframe.model.ElementRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import art.coded.wireframe.model.entity.Element
import kotlinx.coroutines.launch

private val LOG_TAG = PagingViewModel::class.java.simpleName

class PagingViewModel : ViewModel() {
    lateinit var mRepository: ElementRepository
    fun loadData(repository: ElementRepository) {
        mRepository = repository
    }

    fun removeData(element: Element) {
        viewModelScope.launch {
            mRepository.delete(element)
        }
    }

    fun elementList(): LiveData<PagedList<Element>> {
        return mRepository.pagedList
    }
}