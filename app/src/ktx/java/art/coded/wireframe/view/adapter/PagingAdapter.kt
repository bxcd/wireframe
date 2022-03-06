package art.coded.wireframe.view.adapter

import androidx.recyclerview.widget.DiffUtil
import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import art.coded.wireframe.R
import android.widget.TextView
import android.widget.ImageButton
import androidx.core.app.ShareCompat
import androidx.paging.PagedListAdapter
import android.view.View
import art.coded.wireframe.model.entity.Element

private val LOG_TAG = PagingAdapter::class.java.simpleName

class PagingAdapter(diffCallback: DiffUtil.ItemCallback<Element>, var mActivity: Activity) :
    PagedListAdapter<Element, PagingAdapter.ElementViewHolder>(diffCallback) {
    var mLayoutInflater: LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        val itemView = mLayoutInflater.inflate(R.layout.list_item, parent, false)
        return ElementViewHolder(itemView, mActivity)
    }

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        val element = getItem(position)
        holder.mItemView.text = element?.name ?: "No elements"
    }

    class ElementViewHolder(itemView: View, activity: Activity?) :
        RecyclerView.ViewHolder(itemView) {
        val mItemView: TextView
        private val mImageButton: ImageButton

        init {
            mItemView = itemView.findViewById(R.id.textView)
            mImageButton = itemView.findViewById(R.id.list_button_share)
            mImageButton.setOnClickListener { view: View ->
                ShareCompat.IntentBuilder
                    .from(activity!!)
                    .setType("text/plain")
                    .setText(mItemView.text)
                    .startChooser()
            }
        }
    }

    init {
        mLayoutInflater = LayoutInflater.from(mActivity)
    }
}