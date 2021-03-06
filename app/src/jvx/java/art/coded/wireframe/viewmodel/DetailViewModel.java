package art.coded.wireframe.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import art.coded.wireframe.model.entity.Element;
import art.coded.wireframe.model.ElementRepository;

import java.util.List;

public class DetailViewModel extends ViewModel {

    private static final String LOG_TAG = DetailViewModel.class.getSimpleName();

    private LiveData<List<Element>> mData;
    private ElementRepository mRepository;

    public void loadData(Context context) {
        mRepository = new ElementRepository(context);
        mData = mRepository.getAllElements();
    }

    public LiveData<List<Element>> getData() { return mData; }
    public void addData(Element element) { mRepository.insert(element); }
    public void removeAllData() { mRepository.deleteAll(); }
}