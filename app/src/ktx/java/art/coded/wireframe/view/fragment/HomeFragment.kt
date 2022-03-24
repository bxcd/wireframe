package art.coded.wireframe.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import art.coded.wireframe.viewmodel.HomeViewModel
import android.view.View
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import art.coded.wireframe.R
import art.coded.wireframe.databinding.FragmentHomeBinding
import art.coded.wireframe.model.ElementRepository
import art.coded.wireframe.viewmodel.ListViewModel
import art.coded.wireframe.viewmodel.ListViewModelFactory

private val LOG_TAG = HomeFragment::class.java.simpleName

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private var binding: FragmentHomeBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        val sp = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val key = requireContext().getString(R.string.key_username)
        val default = requireContext().getString(R.string.default_username)
        homeViewModel = ViewModelProvider(
            this,
            HomeViewModel.HomeViewModelFactory(sp, key, default)
        ).get(HomeViewModel::class.java)
        return root
    }

    override fun onResume() {
        super.onResume()
        val textView = binding!!.textHome
        homeViewModel.getData().observe(viewLifecycleOwner) { o: String? -> textView.text = o }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}