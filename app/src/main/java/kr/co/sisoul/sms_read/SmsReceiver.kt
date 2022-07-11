package kr.co.sisoul.sms_read

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import timber.log.Timber


class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.d("onReceive")
        if (intent == null) {
            return
        }
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION != intent!!.action) {
            return
        }
        val bundle = intent.extras?: return
        var msg = parseMessage(bundle)
        Timber.d("sms receive!")
        Timber.d("${msg.toString()}")
        msg?.let {
            Timber.d("${msg[0]!!.originatingAddress.toString()}")
            Timber.d("${msg[0]!!.messageBody.toString()}")
        }
//        TODO("Not yet implemented")
    }

    private fun parseMessage(bundle:Bundle) : Array<SmsMessage?> {
        val objs = bundle["pdus"] as Array<Any>?
        val messages = arrayOfNulls<SmsMessage>(
            objs!!.size
        )

        for (i in objs!!.indices) {
            Timber.d("${i.toString()}")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val format = bundle.getString("format")
                messages[i] = SmsMessage.createFromPdu(objs!![i] as ByteArray, format)
            } else {
                messages[i] = SmsMessage.createFromPdu(objs!![i] as ByteArray)
            }
        }

        return messages

    }

}