package art.coded.wireframe.model.entity

import androidx.room.*

@Entity(tableName = "element_table")
class Element(val name: String, @field:ColumnInfo(name = "id") @field:PrimaryKey val id: Int) {

    companion object {
        private val LOG_TAG = Element::class.java.simpleName
    }
}