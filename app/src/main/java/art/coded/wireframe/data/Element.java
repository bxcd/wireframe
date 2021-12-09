package art.coded.wireframe.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="element_table")
public class Element {

    @PrimaryKey @NonNull @ColumnInfo(name="element")
    private String mElement;

    public Element(@NonNull String element) { mElement = element; }
    @NonNull public String getElement() { return mElement; }
}
