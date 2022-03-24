package art.coded.wireframe


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import art.coded.wireframe.model.ElementRepository
import art.coded.wireframe.model.entity.Element
import art.coded.wireframe.model.local.ElementRoomDatabase
import art.coded.wireframe.viewmodel.DetailViewModel
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.io.IOException

// adapted from raywenderlich.com

@RunWith(AndroidJUnit4::class) open class DetailViewModelTest {

    private lateinit var db: ElementRoomDatabase
    @Mock private lateinit var repository: ElementRepository
    @Mock private lateinit var viewModel: DetailViewModel
    @Mock private lateinit var testActionPending: LiveData<Boolean>
    @Mock private lateinit var observer: Observer<Boolean>

    @get:Rule var instantExecutorRule = InstantTaskExecutorRule()

    @Before fun setup() {

        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ElementRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        MockitoAnnotations.openMocks(this)
        repository = spy(ElementRepository(db.elementDao()))
        viewModel = spy(DetailViewModel(repository))
        testActionPending = viewModel.actionPending
    }

//    @Test
//    fun assertElementAdditionTest() {
//        val testElement = Element(name = "Test Name", id = "10")
//        var isPending = testActionPending.value
//        assertNotNull(isPending)
//        isPending?.let { assertTrue(it) }
//        viewModel.addData(testElement)
//        isPending = testActionPending.value
//        assertNotNull(isPending)
//        isPending?.let { assertFalse(it) }
//    }

//    @Test fun assertElementRemovalTest() {
//        var isPending = testActionPending.value
//        assertNotNull(isPending)
//        isPending?.let { assertTrue(it) }
//        viewModel.removeAllData()
//        isPending = testActionPending.value
//        assertNotNull(isPending)
//        isPending?.let { assertFalse(it) }
//    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}