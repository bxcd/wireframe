package art.coded.wireframe.ui.paging;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import art.coded.wireframe.data.ElementComparator;
import art.coded.wireframe.databinding.FragmentPagingBinding;

public class PagingFragment extends Fragment {

    static final String LOG_TAG = PagingFragment.class.getSimpleName();

    private PagingViewModel pagingViewModel;
    private FragmentPagingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPagingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Activity activity = getActivity();

        final RecyclerView recyclerView = binding.rvPaging;
        PagingAdapter pagingAdapter = new PagingAdapter(new ElementComparator(), requireActivity());
        recyclerView.setAdapter(pagingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        pagingViewModel =
                new ViewModelProvider(this).get(PagingViewModel.class);
        pagingViewModel.loadData(requireActivity().getApplication());

        return root;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}