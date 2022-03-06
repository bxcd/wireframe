package art.coded.wireframe.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import art.coded.wireframe.databinding.FragmentCustomBinding;
import art.coded.wireframe.view.custom.CustomView;
import art.coded.wireframe.viewmodel.CustomViewModel;

public class CustomFragment extends Fragment {

    private static final String LOG_TAG = CustomFragment.class.getSimpleName();

    private FragmentCustomBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CustomViewModel customViewModel =
                new ViewModelProvider(this).get(CustomViewModel.class);

        binding = FragmentCustomBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCustom;
        binding.customView.setOnClickListener(view -> {
            int labelRes = ((CustomView) view).getLevelLabelRes();
            String label = getString(labelRes);
            customViewModel.setText(label);
        });
        customViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}