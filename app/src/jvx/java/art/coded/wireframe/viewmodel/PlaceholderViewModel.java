package art.coded.wireframe.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlaceholderViewModel extends ViewModel {

    static final String LOG_TAG = PlaceholderViewModel.class.getSimpleName();

    private MutableLiveData<String> mText;

    public PlaceholderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is placeholder fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}