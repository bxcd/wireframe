package art.coded.wireframe.viewmodel

import androidx.lifecycle.LiveData

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider

private val LOG_TAG = HomeViewModel::class.java.simpleName

class HomeViewModel(private val sharedPreferences: SharedPreferences,
                    private val key: String,
                    private val defaultVal: String)
    : ViewModel() {

    val actionPending = MutableLiveData<Boolean>()

    fun getData(): LiveData<String> {
        actionPending.postValue(true)
        val username = sharedPreferences.getString(key, defaultVal)
        val text = MutableLiveData<String>()
        text.value = String.format("Hello, %s!", username)
        actionPending.postValue(false)
        return text
    }

    class HomeViewModelFactory(private val sharedPreferences: SharedPreferences,
                               private val key: String,
                               private val defaultVal: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(sharedPreferences, key, defaultVal) as T
        }
    }
}