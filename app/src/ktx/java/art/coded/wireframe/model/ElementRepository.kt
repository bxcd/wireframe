package art.coded.wireframe.model

import androidx.lifecycle.LiveData
import art.coded.wireframe.model.local.ElementDao
import androidx.paging.PagedList
import androidx.paging.LivePagedListBuilder
import art.coded.wireframe.model.entity.Element
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val LOG_TAG = ElementRepository::class.java.simpleName

class ElementRepository(private val elementDao: ElementDao) {

    val allElements: LiveData<List<Element>> = elementDao.all

    suspend fun insert(element: Element) { withContext(Dispatchers.IO) { elementDao.insert(element) } }
    suspend fun delete(element: Element) { withContext(Dispatchers.IO) { elementDao.delete(element) } }
    suspend fun deleteAll() { withContext(Dispatchers.IO) { elementDao.deleteAll() } }
    val pagedList: LiveData<PagedList<Element>>
        get() = LivePagedListBuilder(
            elementDao.pagingSource(), 15
        ).build()
}