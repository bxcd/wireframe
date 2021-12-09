package art.coded.wireframe.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities={Element.class}, version=1, exportSchema=false)
public abstract class ElementRoomDatabase extends RoomDatabase {

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
        String[] words = { "test1", "test2", "test3" };

        public PopulateDbAsyncTask(ElementRoomDatabase db) {
            mElementDao = db.elementDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (mElementDao.getAny().length < 1) for (String w : words) mElementDao.insert(new Element(w));
            return null;
        }
    }
}