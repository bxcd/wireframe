package art.coded.wireframe.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import art.coded.wireframe.R
import art.coded.wireframe.viewmodel.DetailViewModel
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import android.content.DialogInterface
import com.google.android.material.snackbar.Snackbar
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import art.coded.wireframe.databinding.FragmentDetailBinding
import art.coded.wireframe.model.entity.Element

class DetailFragment : Fragment() {
    private var detailViewModel: DetailViewModel? = null
    private var binding: FragmentDetailBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        detailViewModel!!.loadData(requireActivity().application)
        val editText = binding!!.detailEditText
        val submitButton = binding!!.detailSubmitButton
        val clearButton = binding!!.detailClearButton
        submitButton.setOnClickListener { submitView: View? ->
            val elementText = editText.text
            if (elementText != null) {
                val element = Element(elementText.toString(), hashCode())
                detailViewModel!!.addData(element)
                editText.setText("")
            }
        }
        clearButton.setOnClickListener { clearView: View? ->
            val dialog = AlertDialog.Builder(requireActivity()).create()
            dialog.setMessage(getString(R.string.dialog_clear_message))
            dialog.setButton(
                AlertDialog.BUTTON_NEGATIVE, getString(R.string.dialog_cancel)
            ) { dialogInterface: DialogInterface?, i: Int -> dialog.dismiss() }
            dialog.setButton(
                AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_confirm)
            ) { dialogInterface: DialogInterface?, i: Int ->
                detailViewModel!!.removeAllData()
                Snackbar.make(
                    clearView!!,
                    getString(R.string.clear_message),
                    Snackbar.LENGTH_LONG
                ).show()
                dialog.dismiss()
            }
            dialog.show()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        val LOG_TAG = DetailFragment::class.java.simpleName
    }
}