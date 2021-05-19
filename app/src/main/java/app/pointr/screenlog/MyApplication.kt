package app.pointr.screenlog

import android.app.Application
import app.pointr.screenlog.utils.ScreenLog


class MyApplication : Application() {
    lateinit var onScreenLog: ScreenLog
        private set

    override fun onCreate() {
        super.onCreate()
        onScreenLog = ScreenLog.builder()
                .context(this)
                .notificationId(1)
                .build()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: MyApplication
    }
}