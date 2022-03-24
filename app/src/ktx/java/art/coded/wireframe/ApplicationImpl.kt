package art.coded.wireframe

import android.app.Application
import android.util.Log
import androidx.work.Configuration

class ApplicationImpl : Application(), Configuration.Provider {
    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder().setMinimumLoggingLevel(Log.INFO).build()
}