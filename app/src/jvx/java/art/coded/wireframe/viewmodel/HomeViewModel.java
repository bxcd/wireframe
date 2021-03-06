package art.coded.wireframe.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.preference.PreferenceManager;

import art.coded.wireframe.R;

public class HomeViewModel extends ViewModel {

    private static final String LOG_TAG = HomeViewModel.class.getSimpleName();

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
    }

    public void loadData(Context context) {

        String username = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.key_username),
                        context.getString(R.string.default_username));

        mText.setValue(String.format("Hello, %s!", username));
    }

    public LiveData<String> getText() {
        return mText;
    }
}