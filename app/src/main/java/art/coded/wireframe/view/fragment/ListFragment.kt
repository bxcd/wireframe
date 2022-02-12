package art.coded.wireframe.view.fragment

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import art.coded.wireframe.viewmodel.ListViewModel
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.View
import androidx.fragment.app.Fragment
import art.coded.wireframe.databinding.FragmentListBinding
import art.coded.wireframe.model.entity.Element
import art.coded.wireframe.view.adapter.ListAdapter

class ListFragment : Fragment() {
    private var listViewModel: ListViewModel? = null
    private var binding: FragmentListBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        val activity: Activity? = activity
        val recyclerView = binding!!.rvList
        val listAdapter = ListAdapter(activity)
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel!!.loadData(requireActivity().application)
        listViewModel!!.data!!.observe(
            viewLifecycleOwner
        ) { elementList: List<Element?>? -> listAdapter.setElements(elementList) }
        val touchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapterPosition = viewHolder.adapterPosition
                val element = listAdapter.getNameByPosition(adapterPosition)
                listViewModel!!.removeData(element)
            }
        })
        touchHelper.attachToRecyclerView(recyclerView)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        val LOG_TAG = ListFragment::class.java.simpleName
    }
}