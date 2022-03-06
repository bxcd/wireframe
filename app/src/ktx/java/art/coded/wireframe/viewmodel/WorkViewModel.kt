package art.coded.wireframe.viewmodel

import androidx.lifecycle.LiveData
import android.app.Application
import art.coded.wireframe.view.work.WorkConstants
import art.coded.wireframe.view.work.DefaultWorker
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.work.*

private val LOG_TAG = WorkViewModel::class.java.simpleName

class WorkViewModel : ViewModel() {
    var mWorkManager: WorkManager? = null
    var workInfo: LiveData<List<WorkInfo>>? = null
    fun loadData(application: Application?) {
        mWorkManager = WorkManager.getInstance(application!!)
        workInfo = mWorkManager!!.getWorkInfosByTagLiveData(WorkConstants.DEFAULT_WORK_TAG)
    }

    fun applyWork() {
        val constraints = Constraints.Builder() // set constraints for OneTimeWorkRequest
            .build()
        val workRequest = OneTimeWorkRequest.Builder(DefaultWorker::class.java)
            .setConstraints(constraints)
            .addTag(WorkConstants.DEFAULT_WORK_TAG)
            .build()
        var workContinuation = mWorkManager!!.beginUniqueWork(
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
        mWorkManager!!.cancelUniqueWork(WorkConstants.DEFAULT_WORK_NAME)
    }
}