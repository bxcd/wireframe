package art.coded.wireframe.viewmodel;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;

import art.coded.wireframe.model.entity.Element;
import art.coded.wireframe.model.local.ElementDao;
import art.coded.wireframe.model.source.ElementPagingSource;
import art.coded.wireframe.model.ElementRepository;
import art.coded.wireframe.model.local.ElementRoomDatabase;
import art.coded.wireframe.AppExecutors;

public class PagingViewModel extends ViewModel {

    Application mApplication;
    ElementRoomDatabase mDatabase;
    ElementRepository mRepository;

    public void loadData(Application application) {
        mApplication = application;
        mDatabase = ElementRoomDatabase.getInstance(application);
        mRepository = new ElementRepository(application);
        ElementDao dao = mDatabase.elementDao();
        Pager<Integer, Element> pager = new Pager<Integer, Element>(
                new PagingConfig(10),
                null,
                () -> new ElementPagingSource("", AppExecutors.getInstance().getDiskIO()));
    }

    public void removeData(Element element) { mRepository.delete(element); }
}
