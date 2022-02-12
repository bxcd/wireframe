package art.coded.wireframe.view.adapter

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import art.coded.wireframe.R
import android.widget.TextView
import android.widget.ImageButton
import androidx.core.app.ShareCompat
import android.view.View
import art.coded.wireframe.model.entity.Element

class ListAdapter(var mActivity: Activity?) :
    RecyclerView.Adapter<ListAdapter.ElementViewHolder>() {
    var mLayoutInflater: LayoutInflater
    var mAllElements: List<Element?>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        val itemView = mLayoutInflater.inflate(R.layout.list_item, parent, false)
        return ElementViewHolder(itemView, mActivity)
    }

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        if (mAllElements != null) {
            val element = mAllElements!![position]
            holder.mItemView.text = element?.name ?: "No elements"
        }
    }

    override fun getItemCount(): Int {
        return if (mAllElements == null) 0 else mAllElements!!.size
    }

    fun setElements(elements: List<Element?>?) {
        mAllElements = elements
        notifyDataSetChanged()
    }

    fun getNameByPosition(position: Int): Element? {
        return mAllElements!![position]
    }

    class ElementViewHolder(itemView: View, activity: Activity?) :
        RecyclerView.ViewHolder(itemView) {
        val mItemView: TextView
        private val mImageButton: ImageButton

        init {
            mItemView = itemView.findViewById(R.id.textView)
            mImageButton = itemView.findViewById(R.id.list_button_share)
            mImageButton.setOnClickListener { view: View? ->
                ShareCompat.IntentBuilder
                    .from(activity!!)
                    .setType("text/plain")
                    .setText(mItemView.text)
                    .startChooser()
            }
        }
    }

    companion object {
        val LOG_TAG = ListAdapter::class.java.simpleName
    }

    init {
        mLayoutInflater = LayoutInflater.from(mActivity)
    }
}