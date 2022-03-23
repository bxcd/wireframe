package art.coded.wireframe.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import art.coded.wireframe.viewmodel.CustomViewModel
import android.view.View
import androidx.fragment.app.Fragment
import art.coded.wireframe.databinding.FragmentCustomBinding

private val LOG_TAG = CustomFragment::class.java.simpleName

class CustomFragment : Fragment() {
    private var binding: FragmentCustomBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val customViewModel = ViewModelProvider(this).get(CustomViewModel::class.java)
        binding = FragmentCustomBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        val textView = binding!!.textCustom
        val customView = binding!!.customView
        customView.setOnClickListener {
            val labelRes = customView.levelLabelRes
            val label = getString(labelRes)
            customViewModel.text.value = label
        }
        customViewModel.text
            .observe(viewLifecycleOwner) { o: String? -> textView.text = o }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}