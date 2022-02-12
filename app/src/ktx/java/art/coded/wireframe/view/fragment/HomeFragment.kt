package art.coded.wireframe.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import art.coded.wireframe.viewmodel.HomeViewModel
import android.view.View
import androidx.fragment.app.Fragment
import art.coded.wireframe.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var homeViewModel: HomeViewModel? = null
    private var binding: FragmentHomeBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        return root
    }

    override fun onResume() {
        super.onResume()
        val textView = binding!!.textHome
        homeViewModel!!.loadData(requireContext())
        homeViewModel!!.text.observe(viewLifecycleOwner) { o: String? -> textView.text = o }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        val LOG_TAG = HomeFragment::class.java.simpleName
    }
}