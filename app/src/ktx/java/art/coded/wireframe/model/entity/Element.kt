package art.coded.wireframe.model.entity

import androidx.room.*

private val LOG_TAG = Element::class.java.simpleName

@Entity(tableName = "element_table")
class Element(val name: String, @field:ColumnInfo(name = "id") @field:PrimaryKey val id: String)