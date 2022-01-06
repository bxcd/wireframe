package art.coded.wireframe.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="element_table")
public class Element {

    private static final String LOG_TAG = Element.class.getSimpleName();

    @PrimaryKey @NonNull @ColumnInfo(name="id")
    private Integer mId;
    private String mName;

    public Element(@NonNull String name, Integer id) {
        mId = id;
        mName = name;
    }
    @NonNull public Integer getId() { return mId; }
    @NonNull public String getName() { return mName; }
}
