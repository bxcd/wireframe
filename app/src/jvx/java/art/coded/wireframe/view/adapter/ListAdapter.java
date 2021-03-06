package art.coded.wireframe.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.RecyclerView;

import art.coded.wireframe.R;
import art.coded.wireframe.model.entity.Element;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ElementViewHolder> {

    private static final String LOG_TAG = ListAdapter.class.getSimpleName();

    LayoutInflater mLayoutInflater;
    List<Element> mAllElements;
    Activity mActivity;

    public ListAdapter(Activity activity) {

        mActivity = activity;
        mLayoutInflater = LayoutInflater.from(activity);
    }

    @NonNull @Override  public ElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = mLayoutInflater.inflate(R.layout.list_item, parent, false);
        return new ElementViewHolder(itemView, mActivity);
    }

    @Override  public void onBindViewHolder(@NonNull ElementViewHolder holder, int position) {

        if (mAllElements != null) {

            Element element = mAllElements.get(position);
            holder.mItemView.setText(element == null ? "No elements" : element.getName());
        }
    }

    @Override  public int getItemCount() {

        return mAllElements == null ? 0 : mAllElements.size();
    }

    public void setElements(List<Element> elements) {
        mAllElements = elements;
        notifyDataSetChanged();
    }

    public Element getNameByPosition(int position) { return mAllElements.get(position); }

    static class ElementViewHolder extends RecyclerView.ViewHolder {

        private final TextView mItemView;
        private final ImageButton mImageButton;

        public ElementViewHolder(@NonNull View itemView, Activity activity) {

            super(itemView);
            mItemView = itemView.findViewById(R.id.textView);
            mImageButton = itemView.findViewById(R.id.list_button_share);
            mImageButton.setOnClickListener(view -> {

                ShareCompat.IntentBuilder
                        .from(activity)
                        .setType("text/plain")
                        .setText(mItemView.getText())
                        .startChooser();
            });
        }
    }
}