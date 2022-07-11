package kr.co.sisoul.sms_read

import android.app.Application
import timber.log.Timber

class SmsReader : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}