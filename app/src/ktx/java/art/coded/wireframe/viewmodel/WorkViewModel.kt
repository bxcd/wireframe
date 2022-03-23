package art.coded.wireframe.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import art.coded.wireframe.view.work.WorkConstants
import art.coded.wireframe.view.work.DefaultWorker
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.work.*

private val LOG_TAG = WorkViewModel::class.java.simpleName

class WorkViewModel : ViewModel() {
    lateinit var mWorkManager: WorkManager
    lateinit var workInfo: LiveData<List<WorkInfo>>
    fun loadData(context: Context?) {
        mWorkManager = WorkManager.getInstance(context!!)
        workInfo = mWorkManager.getWorkInfosByTagLiveData(WorkConstants.DEFAULT_WORK_TAG)
    }

    fun applyWork() {
        val constraints = Constraints.Builder() // set constraints for OneTimeWorkRequest
            .build()
        val workRequest = OneTimeWorkRequest.Builder(DefaultWorker::class.java)
            .setConstraints(constraints)
            .addTag(WorkConstants.DEFAULT_WORK_TAG)
            .build()
        var workContinuation = mWorkManager.beginUniqueWork(
            WorkConstants.DEFAULT_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
        workContinuation.enqueue()
        workContinuation = workContinuation.then(workRequest)
        //         chain additional requests
        workContinuation.enqueue()

//        mWorkManager.enqueue(workRequest);
        Log.d(LOG_TAG, "enqueued")
    }

    fun cancelWork() {
        mWorkManager.cancelUniqueWork(WorkConstants.DEFAULT_WORK_NAME)
    }
}