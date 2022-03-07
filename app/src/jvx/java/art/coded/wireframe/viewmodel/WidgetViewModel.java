package art.coded.wireframe.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import art.coded.wireframe.model.ElementRepository;
import art.coded.wireframe.model.entity.Element;

public class WidgetViewModel extends ViewModel {

    private static final String LOG_TAG = WidgetViewModel.class.getSimpleName();

    private LiveData<List<Element>> mData;

    public void loadData(Context context) {
        ElementRepository mRepository = new ElementRepository(context);
        mData = mRepository.getAllElements();
    }

    public LiveData<List<Element>> getData() {
        return mData;
    }
}