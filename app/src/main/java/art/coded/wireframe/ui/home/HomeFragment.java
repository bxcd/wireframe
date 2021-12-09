package art.coded.wireframe.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import art.coded.wireframe.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        return root;
    }

    @Override public void onResume() {
        super.onResume();
        final TextView textView = binding.textHome;
        homeViewModel.loadData(requireContext());
        homeViewModel.getText().observe(getViewLifecycleOwner(), o -> textView.setText(o));
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}