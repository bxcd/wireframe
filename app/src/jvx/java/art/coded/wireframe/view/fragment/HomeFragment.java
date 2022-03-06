package art.coded.wireframe.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import art.coded.wireframe.databinding.FragmentHomeBinding;
import art.coded.wireframe.viewmodel.HomeViewModel;

// TODO: Toggle app theme style by custom View
public class HomeFragment extends Fragment {

    private static final String LOG_TAG = HomeFragment.class.getSimpleName();

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
        homeViewModel.getText().observe(
                getViewLifecycleOwner(), textView::setText);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}