package art.coded.wireframe.ui.paging;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;

import java.util.List;

import art.coded.wireframe.data.Element;
import art.coded.wireframe.data.ElementDao;
import art.coded.wireframe.data.ElementPagingSource;
import art.coded.wireframe.data.ElementRepository;
import art.coded.wireframe.data.ElementRoomDatabase;
import art.coded.wireframe.ui.AppExecutors;

public class PagingViewModel extends ViewModel {

    Application mApplication;
    ElementRoomDatabase mDatabase;
    ElementRepository mRepository;

    void loadData(Application application) {
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
