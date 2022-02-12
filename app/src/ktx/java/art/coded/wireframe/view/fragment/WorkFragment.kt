package art.coded.wireframe.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import art.coded.wireframe.viewmodel.WorkViewModel
import androidx.work.WorkInfo
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import art.coded.wireframe.databinding.FragmentWorkBinding

class WorkFragment : Fragment() {
    private var mViewModel: WorkViewModel? = null
    private var binding: FragmentWorkBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        val progressBar = binding!!.workProgressBar
        val startButton = binding!!.workStartButton
        val cancelButton = binding!!.workCancelButton
        mViewModel = ViewModelProvider(this).get(WorkViewModel::class.java)
        mViewModel!!.loadData(requireActivity().application)
        startButton.setOnClickListener { v: View? -> mViewModel!!.applyWork() }
        cancelButton.setOnClickListener { v: View? -> mViewModel!!.cancelWork() }
        mViewModel!!.workInfo!!.observe(viewLifecycleOwner) { workInfoList: List<WorkInfo?>? ->
            if (workInfoList == null || workInfoList.isEmpty()) return@observe
            val workInfo = workInfoList[0]
            if (workInfo!!.state.isFinished) {
                startButton.visibility = View.VISIBLE
                cancelButton.visibility = View.INVISIBLE
                progressBar.visibility = View.INVISIBLE
                Log.d(LOG_TAG, "finished")
            } else {
                startButton.visibility = View.INVISIBLE
                cancelButton.visibility = View.VISIBLE
                progressBar.visibility = View.VISIBLE
                Log.d(LOG_TAG, "working")
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        val LOG_TAG = WorkFragment::class.java.simpleName
    }
}