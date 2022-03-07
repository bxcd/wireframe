package art.coded.wireframe.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import art.coded.wireframe.model.entity.Element;
import art.coded.wireframe.model.ElementRepository;

public class PagingViewModel extends ViewModel {

    private final String LOG_TAG = PagingViewModel.class.getSimpleName();

    ElementRepository mRepository;

    public void loadData(Context context) {
        mRepository = new ElementRepository(context);
    }

    public void removeData(Element element) { mRepository.delete(element); }
    public LiveData<PagedList<Element>> elementList() {
        return (mRepository.getPagedList());
    }
}
