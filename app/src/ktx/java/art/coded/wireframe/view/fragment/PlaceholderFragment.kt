package art.coded.wireframe.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import art.coded.wireframe.viewmodel.PlaceholderViewModel
import android.view.View
import androidx.fragment.app.Fragment
import art.coded.wireframe.databinding.FragmentPlaceholderBinding

class PlaceholderFragment : Fragment() {
    private var placeholderViewModel: PlaceholderViewModel? = null
    private var binding: FragmentPlaceholderBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        placeholderViewModel = ViewModelProvider(this).get(
            PlaceholderViewModel::class.java
        )
        binding = FragmentPlaceholderBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        val textView = binding!!.textPlaceholder
        placeholderViewModel!!.text
            .observe(viewLifecycleOwner) { o: String? -> textView.text = o }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        val LOG_TAG = PlaceholderFragment::class.java.simpleName
    }
}