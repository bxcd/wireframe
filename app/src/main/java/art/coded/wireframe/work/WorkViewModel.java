package art.coded.wireframe.work;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.List;

public class WorkViewModel extends ViewModel {

    WorkManager mWorkManager;
    LiveData<List<WorkInfo>> mWorkInfo;

    public WorkViewModel(Application application) {

        mWorkManager = WorkManager.getInstance(application);
        mWorkInfo = mWorkManager.getWorkInfosByTagLiveData(WorkConstants.DEFAULT_WORK_TAG);
    }

    LiveData<List<WorkInfo>> getWorkInfo() { return mWorkInfo; }

    void applyWork() {

//        WorkContinuation workContinuation = mWorkManager.beginUniqueWork(
//                WorkConstants.DEFAULT_WORK_NAME,
//                ExistingWorkPolicy.REPLACE,
//                OneTimeWorkRequest.from(DifferentWorker.class)
//        ); workContinuation.enqueue();

        Constraints constraints = new Constraints.Builder()
                // set constraints for OneTimeWorkRequest
                .build();

        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(DefaultWorker.class)
                .setConstraints(constraints)
                .addTag(WorkConstants.DEFAULT_WORK_TAG)
                .build();

//        workContinuation = workContinuation.then(workRequest);
//         chain additional requests
//        workContinuation.enqueue();

        mWorkManager.enqueue(workRequest);
    }

    void cancelWork() {
        mWorkManager.cancelUniqueWork(WorkConstants.DEFAULT_WORK_TAG);
    }
}
