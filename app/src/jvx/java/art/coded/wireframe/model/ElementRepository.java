package art.coded.wireframe.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;

import art.coded.wireframe.model.entity.Element;
import art.coded.wireframe.model.local.ElementDao;
import art.coded.wireframe.model.local.ElementRoomDatabase;

public class ElementRepository {

    private static final String LOG_TAG = ElementRepository.class.getSimpleName();

    private ElementDao mElementDao;
    private LiveData<List<Element>> mAllElements;

    public ElementRepository(Application application) {
        ElementRoomDatabase db = ElementRoomDatabase.getInstance(application);
        mElementDao = db.elementDao();
        mAllElements = mElementDao.getAll();
    }

    public LiveData<List<Element>> getAllElements() { return mAllElements; }
    public void insert(Element element) { new InsertAsyncTask(mElementDao).execute(element); }
    public void delete(Element element) { new DeleteAsyncTask(mElementDao).execute(element); }
    public void deleteAll() { new DeleteAllAsyncTask(mElementDao).execute(); }
    public LiveData<PagedList<Element>> getPagedList() {
        return new LivePagedListBuilder<>(mElementDao.pagingSource(), 15).build();
    }

    private static class InsertAsyncTask extends AsyncTask<Element, Void, Void> {

        ElementDao mAsyncTaskDao;
        public InsertAsyncTask(ElementDao dao) { mAsyncTaskDao = dao; }
        @Override protected Void doInBackground(Element... elements) {
            mAsyncTaskDao.insert(elements[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Element, Void, Void> {

        ElementDao mAsyncTaskDao;
        public DeleteAsyncTask(ElementDao dao) { mAsyncTaskDao = dao; }
        @Override protected Void doInBackground(Element... elements) {
            mAsyncTaskDao.delete(elements[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        ElementDao mAsyncTaskDao;
        public DeleteAllAsyncTask(ElementDao dao) { mAsyncTaskDao = dao; }
        @Override protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}