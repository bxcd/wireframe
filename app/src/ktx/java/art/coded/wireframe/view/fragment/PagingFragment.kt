package art.coded.wireframe.view.fragment

import androidx.paging.PagedList
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import art.coded.wireframe.viewmodel.PagingViewModel
import art.coded.wireframe.view.adapter.PagingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import androidx.fragment.app.Fragment
import art.coded.wireframe.databinding.FragmentPagingBinding
import art.coded.wireframe.model.entity.Element
import art.coded.wireframe.model.entity.ElementComparator

class PagingFragment : Fragment() {
    private var pagingViewModel: PagingViewModel? = null
    private var binding: FragmentPagingBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPagingBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        val activity: Activity? = activity
        val recyclerView = binding!!.rvPaging
        val pagingAdapter = PagingAdapter(ElementComparator(), requireActivity())
        recyclerView.adapter = pagingAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        pagingViewModel = ViewModelProvider(this).get(PagingViewModel::class.java)
        pagingViewModel!!.loadData(requireActivity().application)
        //        List<Element> elements = pagingViewModel.elementList().getValue();
        pagingViewModel!!.elementList()!!
            .observe(requireActivity()) { pagedList: PagedList<Element> ->
                pagingAdapter.submitList(
                    pagedList
                )
            }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        val LOG_TAG = PagingFragment::class.java.simpleName
    }
}