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

private val LOG_TAG = ListAdapter::class.java.simpleName

class ListAdapter(val activity: Activity?) :
    RecyclerView.Adapter<ListAdapter.ElementViewHolder>() {
    private var layoutInflater: LayoutInflater = LayoutInflater.from(activity)
    private var allElements: List<Element>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        val itemView = layoutInflater.inflate(R.layout.list_item, parent, false)
        return ElementViewHolder(itemView, activity)
    }

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        if (allElements != null) {
            val element = allElements!![position]
            holder.textView.text = element.name ?: "No elements"
        }
    }

    override fun getItemCount(): Int {
        return if (allElements == null) 0 else allElements!!.size
    }

    fun setElements(elements: List<Element>) {
        allElements = elements
        notifyDataSetChanged()
    }

    fun getNameByPosition(position: Int): Element {
        return allElements!![position]
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