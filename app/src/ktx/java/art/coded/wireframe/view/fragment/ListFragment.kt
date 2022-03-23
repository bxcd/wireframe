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
import art.coded.wireframe.model.ElementRepository
import art.coded.wireframe.model.entity.Element
import art.coded.wireframe.model.local.ElementRoomDatabase
import art.coded.wireframe.view.adapter.ListAdapter
import art.coded.wireframe.viewmodel.ListViewModelFactory

private val LOG_TAG = ListFragment::class.java.simpleName

class ListFragment : Fragment() {
    private var binding: FragmentListBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        val activity: Activity? = activity
        val recyclerView = binding!!.rvList
        val listAdapter = ListAdapter(activity)
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        listAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        val db: ElementRoomDatabase = ElementRoomDatabase.getInstance(requireContext().applicationContext)
        val listViewModel = ViewModelProvider(
            this,
            ListViewModelFactory(ElementRepository(db.elementDao())))
                .get(ListViewModel::class.java)
        listViewModel.getData().observe(
            viewLifecycleOwner
        ) { elementList: List<Element> -> listAdapter.setElements(elementList) }
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
                listViewModel.removeData(element)
            }
        })
        touchHelper.attachToRecyclerView(recyclerView)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}