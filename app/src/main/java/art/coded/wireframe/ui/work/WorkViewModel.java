package art.coded.wireframe.ui.work;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.Constraints;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.List;

public class WorkViewModel extends ViewModel {

    private static final String LOG_TAG = WorkViewModel.class.getSimpleName();

    WorkManager mWorkManager;
    LiveData<List<WorkInfo>> mWorkInfo;

    public void loadData(Application application) {

        mWorkManager = WorkManager.getInstance(application);
        mWorkInfo = mWorkManager.getWorkInfosByTagLiveData(WorkConstants.DEFAULT_WORK_TAG);
    }

    LiveData<List<WorkInfo>> getWorkInfo() { return mWorkInfo; }

    void applyWork() {

        Constraints constraints = new Constraints.Builder()
                // set constraints for OneTimeWorkRequest
                .build();

        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(DefaultWorker.class)
                .setConstraints(constraints)
                .addTag(WorkConstants.DEFAULT_WORK_TAG)
                .build();

        WorkContinuation workContinuation = mWorkManager.beginUniqueWork(
                WorkConstants.DEFAULT_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                workRequest
        ); workContinuation.enqueue();

        workContinuation = workContinuation.then(workRequest);
//         chain additional requests
        workContinuation.enqueue();

//        mWorkManager.enqueue(workRequest);

        Log.d(LOG_TAG, "enqueued");
    }

    void cancelWork() {
        mWorkManager.cancelUniqueWork(WorkConstants.DEFAULT_WORK_NAME);
    }
}
