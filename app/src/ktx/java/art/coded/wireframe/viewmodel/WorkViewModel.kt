package art.coded.wireframe.viewmodel

import androidx.lifecycle.LiveData
import art.coded.wireframe.view.work.WorkConstants
import art.coded.wireframe.view.work.DefaultWorker
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.*

private val LOG_TAG = WorkViewModel::class.java.simpleName

class WorkViewModel(private val workManager: WorkManager) : ViewModel() {

    val actionPending = MutableLiveData<Boolean>()

    fun getInfo(): LiveData<List<WorkInfo>> {
        actionPending.postValue(true)
        val workInfo: LiveData<List<WorkInfo>> = workManager.getWorkInfosByTagLiveData(WorkConstants.DEFAULT_WORK_TAG)
        actionPending.postValue(false)
        return workInfo
    }

    fun applyWork() {
        actionPending.postValue(true)
        val constraints = Constraints.Builder() // set constraints for OneTimeWorkRequest
            .build()
        val workRequest = OneTimeWorkRequest.Builder(DefaultWorker::class.java)
            .setConstraints(constraints)
            .addTag(WorkConstants.DEFAULT_WORK_TAG)
            .build()
        var workContinuation = workManager.beginUniqueWork(
            WorkConstants.DEFAULT_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
        workContinuation.enqueue()
        workContinuation = workContinuation.then(workRequest)
        //         chain additional requests
        workContinuation.enqueue()

//        workManager.enqueue(workRequest);
        Log.d(LOG_TAG, "enqueued")
        actionPending.postValue(false)
    }

    fun cancelWork() {
        actionPending.postValue(true)
        workManager.cancelUniqueWork(WorkConstants.DEFAULT_WORK_NAME)
        actionPending.postValue(false)
    }
}

class WorkViewModelFactory(private val workManager: WorkManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WorkViewModel(workManager) as T
    }
}