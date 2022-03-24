package art.coded.wireframe

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.WorkManager
import androidx.work.WorkManagerInitializer
import art.coded.wireframe.viewmodel.WorkViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

// adapted from raywenderlich.com

@RunWith(AndroidJUnit4::class) open class WorkViewModelTest {

    @Mock private lateinit var workManager: WorkManager
    @Mock private lateinit var viewModel: WorkViewModel
    @Mock private lateinit var testActionPending: LiveData<Boolean>
    @Mock private lateinit var observer: Observer<Boolean>

    @get:Rule var instantExecutorRule = InstantTaskExecutorRule()

    @Before fun setup() {

        MockitoAnnotations.openMocks(this)
        workManager = spy(WorkManagerInitializer().create(ApplicationProvider.getApplicationContext()))
        viewModel = spy(WorkViewModel(workManager))
        testActionPending = viewModel.actionPending
    }

    @Test fun verifyLiveDataChangeTest() {
        assertNotNull(viewModel.getInfo())
        viewModel.actionPending.observeForever(observer)
        verify(observer).onChanged(false)
        viewModel.getInfo()
        verify(observer).onChanged(true)
    }

    @Test fun assertWorkStartedTest() {
        viewModel.getInfo()
        var isPending = testActionPending.value
//        assertNotNull(isPending)
        isPending?.let { assertTrue(it) }
        viewModel.applyWork()
        isPending = testActionPending.value
        assertNotNull(isPending)
        isPending?.let { assertFalse(it) }
//        validate workInfo
    }

    @Test fun assertWorkCanceledTest() {
        viewModel.getInfo()
        var isPending = testActionPending.value
//        assertNotNull(isPending)
        isPending?.let { assertTrue(it) }
        viewModel.cancelWork()
        isPending = testActionPending.value
        assertNotNull(isPending)
        isPending?.let { assertFalse(it) }
//        validate workInfo
    }
}