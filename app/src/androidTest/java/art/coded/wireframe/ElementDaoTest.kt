package art.coded.wireframe

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import art.coded.wireframe.model.entity.Element
import art.coded.wireframe.model.local.ElementRoomDatabase
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.random.Random

// adapted from raywenderlich.com

@Throws(InterruptedException::class)
fun <T> LiveData<T>.getValueBlocking(): T? {
    var value: T? = null
    val latch = CountDownLatch(1)
    val innerObserver = Observer<T> {
        value = it
        latch.countDown()
    }
    observeForever(innerObserver)
    latch.await(2, TimeUnit.SECONDS)
    return value
}

@RunWith(AndroidJUnit4::class) open class ElementDaoTest {

    private lateinit var db: ElementRoomDatabase

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before fun initDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ElementRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @Test fun assertElementInsertionTest() {
        val length: Int = /*Random().nextInt()*/ 99
        for (i in 0 until length)
            db.elementDao().insert(Element(String.format("test%s", i), String.format("%s", i)))
        val elementSize = db.elementDao().all.getValueBlocking()?.size
        assertEquals(elementSize, length)
    }

    @Test fun assertElementDeletionTest() {
        val length: Int = /*Random().nextInt()*/ 99
        for (i in 0 until length)
            db.elementDao().insert(Element(String.format("test%s", i), String.format("%s", i)))
        assertEquals(db.elementDao().all.getValueBlocking()?.size, length)
        for (i in 0 until length)
            db.elementDao().delete(Element(String.format("test%s", i), String.format("%s", i)))
        assertEquals(db.elementDao().all.getValueBlocking()?.size, 0)
    }

    @Test fun assertElementPurgeTest() {
        val length: Int = /*Random().nextInt()*/ 99
        for (i in 0 until length)
            db.elementDao().insert(Element(String.format("test%s", i), String.format("%s", i)))
        assertEquals(db.elementDao().all.getValueBlocking()?.size, length)
        db.elementDao().deleteAll()
        assertEquals(db.elementDao().all.getValueBlocking()?.size, 0)
    }

    @Test fun assertElementAsLiveDataTest() {
        val length: Int = /*Random().nextInt()*/ 99
        for (i in 0 until length)
            db.elementDao().insert(Element(String.format("test%s", i), String.format("%s", i)))
        val liveDataValue = db.elementDao().all.getValueBlocking()
        val pagedLiveDataValue =
            LivePagedListBuilder(db.elementDao().pagingSource(), 15).build().getValueBlocking()
        assertEquals(liveDataValue?.size, length)
        assertEquals(pagedLiveDataValue?.size, length)
        assertEquals(liveDataValue?.size, pagedLiveDataValue?.size)
    }

    @After @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}