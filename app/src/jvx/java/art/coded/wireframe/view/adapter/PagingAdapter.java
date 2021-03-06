package art.coded.wireframe.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import art.coded.wireframe.R;
import art.coded.wireframe.model.entity.Element;

public class PagingAdapter extends PagedListAdapter<Element, PagingAdapter.ElementViewHolder> {

    private static final String LOG_TAG = PagingAdapter.class.getSimpleName();

    LayoutInflater mLayoutInflater;
    Activity mActivity;

    public PagingAdapter(@NonNull DiffUtil.ItemCallback<Element> diffCallback, Activity activity) {
        super(diffCallback);
        mActivity = activity;
        mLayoutInflater = LayoutInflater.from(mActivity);
    }

    @NonNull @Override public ElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.list_item, parent, false);
        return new PagingAdapter.ElementViewHolder(itemView, mActivity);
    }

    @Override public void onBindViewHolder(@NonNull ElementViewHolder holder, int position) {
        Element element = getItem(position);
        holder.mItemView.setText(element == null ? "No elements" : element.getName());
    }

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
