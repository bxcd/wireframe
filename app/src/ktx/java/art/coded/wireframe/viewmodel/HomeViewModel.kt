package art.coded.wireframe.viewmodel

import androidx.lifecycle.LiveData

import art.coded.wireframe.R
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager

private val LOG_TAG = HomeViewModel::class.java.simpleName

class HomeViewModel : ViewModel() {
    private val mText = MutableLiveData<String>()
    fun loadData(sharedPreferences: SharedPreferences, key: String, defaultVal: String) {
        val username = sharedPreferences.getString(key, defaultVal)
        mText.value = String.format("Hello, %s!", username)
    }

    val text: LiveData<String?>
        get() = mText
}