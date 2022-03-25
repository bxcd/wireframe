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

class PagingAdapter(diffCallback: DiffUtil.ItemCallback<Element>, val activity: Activity) :
    PagedListAdapter<Element, PagingAdapter.ElementViewHolder>(diffCallback) {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(activity)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        val itemView = layoutInflater.inflate(R.layout.list_item, parent, false)
        return ElementViewHolder(itemView, activity)
    }

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        val element = getItem(position)
        holder.textView.text = element?.name ?: "No elements"
    }

    class ElementViewHolder(itemView: View, activity: Activity?) :
        RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        private val imageButton: ImageButton = itemView.findViewById(R.id.list_button_share)

        init {
            imageButton.setOnClickListener { view: View ->
                ShareCompat.IntentBuilder
                    .from(activity!!)
                    .setType("text/plain")
                    .setText(textView.text)
                    .startChooser()
            }
        }
    }
}