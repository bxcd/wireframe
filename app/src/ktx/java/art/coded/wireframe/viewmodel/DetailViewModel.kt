package art.coded.wireframe.viewmodel

import androidx.lifecycle.LiveData
import android.content.Context
import art.coded.wireframe.model.ElementRepository
import androidx.lifecycle.ViewModel
import art.coded.wireframe.model.entity.Element

private val LOG_TAG = DetailViewModel::class.java.simpleName

class DetailViewModel : ViewModel() {
    var data : LiveData<List<Element>>? = null
        private set
    private var mRepository: ElementRepository? = null
    fun loadData(context: Context) {
        mRepository = ElementRepository(context)
        data = mRepository!!.allElements
    }

    fun addData(element: Element) {
        mRepository!!.insert(element)
    }

    fun removeAllData() {
        mRepository!!.deleteAll()
    }
}