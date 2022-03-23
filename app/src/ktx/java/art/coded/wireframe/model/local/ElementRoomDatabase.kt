package art.coded.wireframe.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import android.os.AsyncTask
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import android.content.Context
import art.coded.wireframe.model.entity.Element

@Database(entities = [Element::class], version = 1, exportSchema = false)
abstract class ElementRoomDatabase : RoomDatabase() {
    abstract fun elementDao(): ElementDao
    private class PopulateDbAsyncTask(db: ElementRoomDatabase?) : AsyncTask<Void?, Void?, Void?>() {
        var mElementDao: ElementDao
        override fun doInBackground(vararg voids: Void?): Void? {
            if (mElementDao.any.size < 1) for (i in 0..99) {
                mElementDao.insert(Element(String.format("test%s", i), i))
            }
            return null
        }

        init {
            mElementDao = db!!.elementDao()
        }
    }

    companion object {
        private lateinit var INSTANCE: ElementRoomDatabase
        fun getInstance(context: Context?): ElementRoomDatabase {
            if (!::INSTANCE.isInitialized) {
                synchronized(ElementRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context!!, ElementRoomDatabase::class.java, "element_database"
                    )
                        .addCallback(sRoomDatabaseCallback)
                        .build()
                }
            }
            return INSTANCE
        }

        private val sRoomDatabaseCallback: Callback = object : Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsyncTask(INSTANCE).execute()
            }
        }
    }
}