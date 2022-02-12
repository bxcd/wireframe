package art.coded.wireframe.viewmodel

import androidx.lifecycle.LiveData

import art.coded.wireframe.R
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager

class HomeViewModel : ViewModel() {
    private val mText: MutableLiveData<String?>
    fun loadData(context: Context) {
        val username = PreferenceManager.getDefaultSharedPreferences(context)
            .getString(
                context.getString(R.string.key_username),
                context.getString(R.string.default_username)
            )
        mText.value = String.format("Hello, %s!", username)
    }

    val text: LiveData<String?>
        get() = mText

    companion object {
        val LOG_TAG = HomeViewModel::class.java.simpleName
    }

    init {
        mText = MutableLiveData()
    }
}