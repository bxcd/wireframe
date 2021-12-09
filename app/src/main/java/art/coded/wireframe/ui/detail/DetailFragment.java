package art.coded.wireframe.ui.detail;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import art.coded.wireframe.R;
import art.coded.wireframe.data.Element;
import art.coded.wireframe.databinding.FragmentDetailBinding;
import com.google.android.material.snackbar.Snackbar;

public class DetailFragment extends Fragment {

    private DetailViewModel detailViewModel;
    private FragmentDetailBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        detailViewModel.loadData(requireActivity().getApplication());
        EditText editText = binding.detailEditText;
        Button submitButton = binding.detailSubmitButton;
        Button clearButton = binding.detailClearButton;

        submitButton.setOnClickListener(submitView -> {
                Editable elementText = editText.getText();
                if (elementText != null) {
                    Element element = new Element(elementText.toString());
                    detailViewModel.addData(element);
                    editText.setText("");
                }
        });

        clearButton.setOnClickListener(clearView -> {
            AlertDialog dialog = new AlertDialog.Builder(requireActivity()).create();
            dialog.setMessage(getString(R.string.dialog_clear_message));
            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.dialog_cancel),
                    (dialogInterface, i) -> dialog.dismiss());
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_confirm),
                    (dialogInterface, i) -> {
                        detailViewModel.removeAllData();
                        Snackbar.make(
                                clearView,
                                getString(R.string.clear_message),
                                Snackbar.LENGTH_LONG).show();
                        dialog.dismiss();
            });
            dialog.show();
        });

        return root;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}