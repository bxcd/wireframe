package art.coded.wireframe.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.WorkInfo;

import art.coded.wireframe.databinding.FragmentWorkBinding;
import art.coded.wireframe.viewmodel.WorkViewModel;

public class WorkFragment extends Fragment {

    static final String LOG_TAG = WorkFragment.class.getSimpleName();

    private WorkViewModel mViewModel;
    private FragmentWorkBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWorkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ProgressBar progressBar = binding.workProgressBar;
        Button startButton = binding.workStartButton;
        Button cancelButton = binding.workCancelButton;

        mViewModel = new ViewModelProvider(this).get(WorkViewModel.class);
        mViewModel.loadData(requireActivity().getApplication());

        startButton.setOnClickListener(v -> mViewModel.applyWork());
        cancelButton.setOnClickListener(v -> mViewModel.cancelWork());

        mViewModel.getWorkInfo().observe(getViewLifecycleOwner(), workInfoList -> {

            if (workInfoList == null || workInfoList.isEmpty()) return;

            WorkInfo workInfo = workInfoList.get(0);
            if (workInfo.getState().isFinished()) {

                startButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                Log.d(LOG_TAG, "finished");

            } else {

                startButton.setVisibility(View.INVISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                Log.d(LOG_TAG, "working");
            }
        });

        return root;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}