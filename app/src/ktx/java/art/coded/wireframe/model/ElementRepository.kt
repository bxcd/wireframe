package art.coded.wireframe.model

import android.app.Application
import androidx.lifecycle.LiveData
import art.coded.wireframe.model.local.ElementDao
import art.coded.wireframe.model.local.ElementRoomDatabase
import android.content.Context
import androidx.paging.PagedList
import androidx.paging.LivePagedListBuilder
import art.coded.wireframe.model.entity.Element
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val LOG_TAG = ElementRepository::class.java.simpleName

class ElementRepository(elementDao: ElementDao) {

    private val mElementDao: ElementDao = elementDao
    val allElements: LiveData<List<Element>> = elementDao.all

    suspend fun insert(element: Element) { withContext(Dispatchers.IO) { mElementDao.insert(element) } }
    suspend fun delete(element: Element) { withContext(Dispatchers.IO) { mElementDao.delete(element) } }
    suspend fun deleteAll() { withContext(Dispatchers.IO) { mElementDao.deleteAll() } }
    val pagedList: LiveData<PagedList<Element>>
        get() = LivePagedListBuilder(
            mElementDao.pagingSource(), 15
        ).build()
}