package art.coded.wireframe.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import art.coded.wireframe.model.entity.Element;
import art.coded.wireframe.model.entity.ElementComparator;
import art.coded.wireframe.databinding.FragmentPagingBinding;
import art.coded.wireframe.view.adapter.PagingAdapter;
import art.coded.wireframe.viewmodel.PagingViewModel;

public class PagingFragment extends Fragment {

    static final String LOG_TAG = PagingFragment.class.getSimpleName();

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

        PagingViewModel pagingViewModel =
                new ViewModelProvider(this).get(PagingViewModel.class);
        pagingViewModel.loadData(requireActivity().getApplication());
//        List<Element> elements = pagingViewModel.elementList().getValue();

        pagingViewModel.elementList().observe(requireActivity(), pagingAdapter::submitList);

        return root;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}