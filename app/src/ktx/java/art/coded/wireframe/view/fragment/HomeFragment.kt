package art.coded.wireframe.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import art.coded.wireframe.viewmodel.HomeViewModel
import android.view.View
import androidx.fragment.app.Fragment
import art.coded.wireframe.databinding.FragmentHomeBinding

private val LOG_TAG = HomeFragment::class.java.simpleName

// TODO: Decouple contexts from business logic (model, viewmodel)
// TODO: Encapsulate logic for each UI functionality into helper methods
// TODO: Replace logic written inside UI lifecycle methods with helper method calls
class HomeFragment : Fragment() {
    private var homeViewModel = HomeViewModel()
    private var binding: FragmentHomeBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        return root
    }

    override fun onResume() {
        super.onResume()
        val textView = binding!!.textHome
        homeViewModel.loadData(requireContext())
        homeViewModel.text.observe(viewLifecycleOwner) { o: String? -> textView.text = o }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}