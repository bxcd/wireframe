package art.coded.wireframe.model.local;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import art.coded.wireframe.model.entity.Element;

@Database(entities={Element.class}, version=1, exportSchema=false)
public abstract class ElementRoomDatabase extends RoomDatabase {

    private static final String LOG_TAG = ElementRoomDatabase.class.getSimpleName();

    public abstract ElementDao elementDao();
    private static ElementRoomDatabase INSTANCE;

    public static ElementRoomDatabase getInstance(final Context context) {

        if (INSTANCE == null) {
            synchronized (ElementRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, ElementRoomDatabase.class, "element_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        } return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =

            new RoomDatabase.Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsyncTask(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        ElementDao mElementDao;

        public PopulateDbAsyncTask(ElementRoomDatabase db) {
            mElementDao = db.elementDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (mElementDao.getAny().length < 1)
                for (int i = 0; i < 100; i++) {
                    mElementDao.insert(new Element(String.format("test%s", i), i));
                }
            return null;
        }
    }
}