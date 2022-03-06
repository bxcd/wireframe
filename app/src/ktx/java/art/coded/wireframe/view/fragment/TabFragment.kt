package art.coded.wireframe.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import art.coded.wireframe.viewmodel.TabViewModel
import android.view.View
import androidx.fragment.app.Fragment
import art.coded.wireframe.databinding.FragmentTabBinding

private val LOG_TAG = TabFragment::class.java.simpleName
private const val ARG_SECTION_NUMBER = "section_number"

/**
 * A custom fragment containing a simple view.
 */
class TabFragment : Fragment() {
    private var tabViewModel = TabViewModel()
    private var binding: FragmentTabBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabViewModel = ViewModelProvider(this).get(TabViewModel::class.java)
        var index = 1
        if (arguments != null) {
            index = requireArguments().getInt(ARG_SECTION_NUMBER)
        }
        tabViewModel.setIndex(index)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        val textView = binding!!.sectionLabel
        tabViewModel.text.observe(viewLifecycleOwner) { o: String? -> textView.text = o }
        return root
    }

    companion object {
        fun newInstance(index: Int): TabFragment {
            val fragment = TabFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}