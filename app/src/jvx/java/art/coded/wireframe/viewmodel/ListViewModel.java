package art.coded.wireframe.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import art.coded.wireframe.model.entity.Element;
import art.coded.wireframe.model.ElementRepository;

import java.util.List;

public class ListViewModel extends ViewModel {

    static final String LOG_TAG = ListViewModel.class.getSimpleName();

    private ElementRepository mRepository;
    private LiveData<List<Element>> mData;

    public void loadData(Application application) {
        mRepository = new ElementRepository(application);
        mData = mRepository.getAllElements();
    }

    public LiveData<List<Element>> getData() {
        return mData;
    }
    public void removeData(Element element) { mRepository.delete(element); }
}