package art.coded.wireframe.model.local;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import art.coded.wireframe.model.entity.Element;

@Dao public interface ElementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) void insert(Element element);
    @Delete void delete(Element element);
    @Query("SELECT * from element_table") LiveData<List<Element>> getAll();
    @Query("SELECT * from element_table LIMIT 1") Element[] getAny();
    @Query("DELETE from element_table") void deleteAll();

    @Query("SELECT * from element_table")
    DataSource.Factory<Integer, Element> pagingSource();
}