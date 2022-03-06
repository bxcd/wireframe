package art.coded.wireframe.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import art.coded.wireframe.databinding.FragmentCustomBinding;
import art.coded.wireframe.viewmodel.CustomViewModel;

public class CustomFragment extends Fragment {

    static final String LOG_TAG = CustomFragment.class.getSimpleName();

    private FragmentCustomBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CustomViewModel customViewModel =
                new ViewModelProvider(this).get(CustomViewModel.class);

        binding = FragmentCustomBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCustom;
        customViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}