package art.coded.wireframe.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import art.coded.wireframe.model.ElementRepository;
import art.coded.wireframe.model.entity.Element;

public class WidgetViewModel extends ViewModel {

    private static final String LOG_TAG = WidgetViewModel.class.getSimpleName();

    private LiveData<List<Element>> mData;

    public void loadData(Application application) {
        ElementRepository mRepository = new ElementRepository(application);
        mData = mRepository.getAllElements();
    }

    public LiveData<List<Element>> getData() {
        return mData;
    }
}