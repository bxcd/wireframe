package art.coded.wireframe.model.entity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class ElementComparator extends DiffUtil.ItemCallback<Element> {

    @Override public boolean areItemsTheSame(@NonNull Element oldItem, @NonNull Element newItem) {
        return oldItem.hashCode() == newItem.hashCode();
    }

    @Override public boolean areContentsTheSame(@NonNull Element oldItem, @NonNull Element newItem) {
        return oldItem.getName().equals(newItem.getName());
    }
}
