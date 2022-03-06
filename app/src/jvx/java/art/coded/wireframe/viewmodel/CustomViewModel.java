package art.coded.wireframe.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CustomViewModel extends ViewModel {

    static final String LOG_TAG = CustomViewModel.class.getSimpleName();

    private final MutableLiveData<String> mText;

    public CustomViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is custom view");
    }

    public LiveData<String> getText() {
        return mText;
    }
}