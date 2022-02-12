package art.coded.wireframe.viewmodel

import androidx.lifecycle.LiveData
import android.app.Application
import art.coded.wireframe.model.ElementRepository
import androidx.lifecycle.ViewModel
import art.coded.wireframe.model.entity.Element

class ListViewModel : ViewModel() {
    private var mRepository: ElementRepository? = null
    var data: LiveData<List<Element?>?>? = null
        private set

    fun loadData(application: Application?) {
        mRepository = ElementRepository(application)
        data = mRepository!!.allElements
    }

    fun removeData(element: Element?) {
        mRepository!!.delete(element)
    }

    companion object {
        val LOG_TAG = ListViewModel::class.java.simpleName
    }
}