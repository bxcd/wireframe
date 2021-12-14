package art.coded.wireframe.ui.detail;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import art.coded.wireframe.data.Element;
import art.coded.wireframe.data.ElementRepository;

import java.util.List;

public class DetailViewModel extends ViewModel {

    static final String LOG_TAG = DetailViewModel.class.getSimpleName();

    private LiveData<List<Element>> mData;
    private ElementRepository mRepository;

    public void loadData(Application application) {
        mRepository = new ElementRepository(application);
        mData = mRepository.getAllElements();
    }

    public LiveData<List<Element>> getData() { return mData; }
    public void addData(Element element) { mRepository.insert(element); }
    public void removeAllData() { mRepository.deleteAll(); }
}