package art.coded.wireframe.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import art.coded.wireframe.model.entity.Element;
import art.coded.wireframe.model.ElementRepository;

public class PagingViewModel extends ViewModel {

    ElementRepository mRepository;

    public void loadData(Application application) {
        mRepository = new ElementRepository(application);
    }

    public void removeData(Element element) { mRepository.delete(element); }
    public LiveData<PagedList<Element>> elementList() {
        return (mRepository.getPagedList());
    }
}
