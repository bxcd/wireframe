package art.coded.wireframe.ui.list;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import art.coded.wireframe.data.Element;
import art.coded.wireframe.databinding.FragmentListBinding;

public class ListFragment extends Fragment {

    static final String LOG_TAG = ListFragment.class.getSimpleName();

    private ListViewModel listViewModel;
    private FragmentListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Activity activity = getActivity();

        final RecyclerView recyclerView = binding.rvList;
        ListAdapter listAdapter = new ListAdapter(activity);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        listViewModel =
                new ViewModelProvider(this).get(ListViewModel.class);
        listViewModel.loadData(requireActivity().getApplication());
        listViewModel.getData().observe(getViewLifecycleOwner(),
                elementList -> listAdapter.setElements(elementList));

        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(
                    @NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int adapterPosition = viewHolder.getAdapterPosition();
                Element element = listAdapter.getElementByPosition(adapterPosition);
                listViewModel.removeData(element);
            }
        }); touchHelper.attachToRecyclerView(recyclerView);

        return root;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}