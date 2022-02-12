package art.coded.wireframe.model.entity

import androidx.recyclerview.widget.DiffUtil

class ElementComparator : DiffUtil.ItemCallback<Element>() {
    override fun areItemsTheSame(oldItem: Element, newItem: Element): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: Element, newItem: Element): Boolean {
        return oldItem.name == newItem.name
    }
}