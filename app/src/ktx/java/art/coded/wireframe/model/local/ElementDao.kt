package art.coded.wireframe.model.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import art.coded.wireframe.model.entity.Element

@Dao
interface ElementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(element: Element?)

    @Delete
    fun delete(element: Element?)

    @get:Query("SELECT * from element_table")
    val all: LiveData<List<Element?>?>?

    @get:Query("SELECT * from element_table LIMIT 1")
    val any: Array<Element?>

    @Query("DELETE from element_table")
    fun deleteAll()

    @Query("SELECT * from element_table")
    fun pagingSource(): DataSource.Factory<Int, Element>
}