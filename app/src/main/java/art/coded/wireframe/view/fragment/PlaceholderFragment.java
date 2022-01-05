package art.coded.wireframe.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import art.coded.wireframe.databinding.FragmentPlaceholderBinding;
import art.coded.wireframe.viewmodel.PlaceholderViewModel;

public class PlaceholderFragment extends Fragment {

    static final String LOG_TAG = PlaceholderFragment.class.getSimpleName();

    private PlaceholderViewModel placeholderViewModel;
    private FragmentPlaceholderBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        placeholderViewModel =
                new ViewModelProvider(this).get(PlaceholderViewModel.class);

        binding = FragmentPlaceholderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPlaceholder;
        placeholderViewModel.getText().observe(getViewLifecycleOwner(), o -> textView.setText(o));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}