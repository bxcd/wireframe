package art.coded.wireframe.viewmodel

import androidx.lifecycle.LiveData
import android.app.Application
import androidx.paging.PagedList
import art.coded.wireframe.model.ElementRepository
import androidx.lifecycle.ViewModel
import art.coded.wireframe.model.entity.Element

private val LOG_TAG = PagingViewModel::class.java.simpleName

class PagingViewModel : ViewModel() {
    var mRepository: ElementRepository? = null
    fun loadData(application: Application) {
        mRepository = ElementRepository(application)
    }

    fun removeData(element: Element) {
        mRepository!!.delete(element)
    }

    fun elementList(): LiveData<PagedList<Element>> {
        return mRepository!!.pagedList
    }
}