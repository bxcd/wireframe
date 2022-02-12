package art.coded.wireframe.model

import androidx.lifecycle.LiveData
import art.coded.wireframe.model.local.ElementDao
import art.coded.wireframe.model.local.ElementRoomDatabase
import android.os.AsyncTask
import android.app.Application
import androidx.paging.PagedList
import androidx.paging.LivePagedListBuilder
import art.coded.wireframe.model.entity.Element

class ElementRepository(application: Application?) {
    private val mElementDao: ElementDao?

    val allElements: LiveData<List<Element?>?>?
    fun insert(element: Element?) { InsertAsyncTask(mElementDao).execute(element) }
    fun delete(element: Element?) { DeleteAsyncTask(mElementDao).execute(element) }
    fun deleteAll() { DeleteAllAsyncTask(mElementDao).execute() }
    val pagedList: LiveData<PagedList<Element>>
        get() = LivePagedListBuilder(
            mElementDao!!.pagingSource(), 15
        ).build()

    private class InsertAsyncTask(var mAsyncTaskDao: ElementDao?) :
        AsyncTask<Element?, Void?, Void?>() {
        override fun doInBackground(vararg elements: Element?): Void? {
            mAsyncTaskDao!!.insert(elements[0])
            return null
        }
    }

    private class DeleteAsyncTask(var mAsyncTaskDao: ElementDao?) :
        AsyncTask<Element?, Void?, Void?>() {
        override fun doInBackground(vararg elements: Element?): Void? {
            mAsyncTaskDao!!.delete(elements[0])
            return null
        }
    }

    private class DeleteAllAsyncTask(var mAsyncTaskDao: ElementDao?) :
        AsyncTask<Void?, Void?, Void?>() {
        override fun doInBackground(vararg voids: Void?): Void? {
            mAsyncTaskDao!!.deleteAll()
            return null
        }
    }

    companion object {
        private val LOG_TAG = ElementRepository::class.java.simpleName
    }

    init {
        val db: ElementRoomDatabase = ElementRoomDatabase.Companion.getInstance(application)
        mElementDao = db.elementDao()
        allElements = mElementDao.all
    }
}