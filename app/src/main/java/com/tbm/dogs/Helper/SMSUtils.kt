package com.tbm.dogs.Helper

import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Handler
import android.telephony.SmsManager
import android.util.Log


class SMSUtils : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //Detect l'envoie de sms
        if (intent.action == SENT_SMS_ACTION_NAME) {
            when (resultCode) {
                Activity.RESULT_OK // Sms sent
                -> Log.e("sms:","sms send")
                    //Toast.makeText(context, "sms_send", Toast.LENGTH_LONG).show()
                SmsManager.RESULT_ERROR_GENERIC_FAILURE // generic failure
                -> Log.e("sms:","sms not send")
                    //Toast.makeText(context, "sms_not_send", Toast.LENGTH_LONG).show()
                SmsManager.RESULT_ERROR_NO_SERVICE // No service
                -> Log.e("sms:","sms not send no service")//Toast.makeText(context, "sms not send no service", Toast.LENGTH_LONG).show()
                SmsManager.RESULT_ERROR_NULL_PDU // null pdu
                -> Log.e("sms:","sms not send null pdu")//Toast.makeText(context, "sms not send null pdu", Toast.LENGTH_LONG).show()
                SmsManager.RESULT_ERROR_RADIO_OFF //Radio off
                -> Log.e("sms:","sms not send radio off")//Toast.makeText(context, "sms not send radio off", Toast.LENGTH_LONG).show()
            }
        } else if (intent.action == DELIVERED_SMS_ACTION_NAME) {
            when (resultCode) {
                Activity.RESULT_OK -> Log.e("sms:","sms da nhan duoc")//Toast.makeText(context, "sms da nhan duoc", Toast.LENGTH_LONG).show()
                Activity.RESULT_CANCELED -> Log.e("sms:","sms khong nhan duoc")//Toast.makeText(context, "sms khong nhan duoc", Toast.LENGTH_LONG).show()
            }
        }//detect la reception d'un sms
    }

    companion object {

        val SENT_SMS_ACTION_NAME = "SMS_SENT"
        val DELIVERED_SMS_ACTION_NAME = "SMS_DELIVERED"

        /**
         * Test if device can send SMS
         * @param context
         * @return
         */
        fun canSendSMS(context: Context): Boolean {
            return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY)
        }

        fun sendSMS(context: Context, phoneNumber: String, message: String) {

            if (!canSendSMS(context)) {
                Log.e("sms:","sms send")//Toast.makeText(context, "khong the gui sms", Toast.LENGTH_LONG).show()
                return
            }

            val sentPI = PendingIntent.getBroadcast(context, 0, Intent(SENT_SMS_ACTION_NAME), 0)
            val deliveredPI = PendingIntent.getBroadcast(context, 0, Intent(DELIVERED_SMS_ACTION_NAME), 0)

            val smsUtils = SMSUtils()
            //register for sending and delivery
            context.registerReceiver(smsUtils, IntentFilter(SMSUtils.SENT_SMS_ACTION_NAME))
            context.registerReceiver(smsUtils, IntentFilter(DELIVERED_SMS_ACTION_NAME))

            val sms = SmsManager.getDefault()
            val parts = sms.divideMessage(message)

            val sendList = ArrayList<PendingIntent>()
            sendList.add(sentPI)

            val deliverList = ArrayList<PendingIntent>()
            deliverList.add(deliveredPI)

            sms.sendMultipartTextMessage(phoneNumber, null, parts, sendList, deliverList)

            //we unsubscribed in 10 seconds
            Handler().postDelayed(Runnable { context.unregisterReceiver(smsUtils) }, 10000)

        }
    }
}