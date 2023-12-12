package com.example.compose.rally.data.sms

import SmsService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsMessage
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.compose.rally.data.account.Account
import com.example.compose.rally.data.account.AccountRepository
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Suppress("DEPRECATION")
class SMSMonitor : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals(ACTION, ignoreCase = true)) {
            val pduArray = intent.extras?.get("pdus") as? Array<*>
            pduArray?.let { processSmsMessages(context, it) }
        }
    }

    private fun processSmsMessages(context: Context, pduArray: Array<*>) {
        val messages =
            pduArray.mapNotNull { SmsMessage.createFromPdu(it as? ByteArray) }.toTypedArray()
        val smsFrom = messages.firstOrNull()?.displayOriginatingAddress
        Log.e("sms", "smsFrom is $smsFrom")

        smsFrom?.let {
            when {
                it.equals("T2 Market", ignoreCase = true) -> processTele2Sms(messages, context)
                it.equals("900", ignoreCase = true) -> processBankSms(messages, context)
            }
        }
    }

    private fun processTele2Sms(messages: Array<SmsMessage>, context: Context) {
        val body = buildSmsBody(messages)
        val group = body.split(" ")

        if (!group[0].first().isDigit()) {
            try {
                val currTime = LocalDateTime.now()
                val income = group[10].split(".")
                AccountRepository.addAccount(
                    Account(
                        name = "Tele2",
                        date = LocalDateTime.of(
                            currTime.year, currTime.month, currTime.dayOfMonth,
                            currTime.hour, currTime.minute
                        ),
                        timesRepeat = 0,
                        cardNumber = 0,
                        balance = income[0].toFloat() + (income[1].toFloat() / 100),
                        category = "Из смс"
                    )
                )
                startSmsService(context, body)
                abortBroadcast()
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    private fun processBankSms(messages: Array<SmsMessage>, context: Context) {
        val body = buildSmsBody(messages)
        val group = body.split(" ")

        try {
            if (group[3] == "Зачислен") {
                processBankDeposit(group)
            } else if (group[2] == "Перевод") {
                processBankTransfer(group)
            } else if (body.matches("[Пп]еревод \\d+р".toRegex())) {
                processTransferSms(body)
            }

            startSmsService(context, body)
            abortBroadcast()
        } catch (e: Exception) {
            // Handle exception
        }
    }

    private fun buildSmsBody(messages: Array<SmsMessage>): String {
        val bodyText = StringBuilder()
        messages.forEach { message ->
            bodyText.append(message.messageBody)
            Log.e("sms", "sms is $message, ${message.messageBody}")
        }
        return bodyText.toString()
    }

    private fun processBankDeposit(group: List<String>) {
        val time = LocalDateTime.parse(group[2])
        val income = group[6].replace("р", "").split(",")
        AccountRepository.addAccount(
            Account(
                name = "Tele2",
                date = time,
                timesRepeat = 0,
                cardNumber = 0,
                balance = income[0].toFloat() + (income[1].toFloat() / 100),
                category = "Из смс"
            )
        )
    }

    private fun processBankTransfer(group: List<String>) {
        val income = group[6].replace("р", "").split(",")
        AccountRepository.addAccount(
            Account(
                name = group[0],
                date = LocalDateTime.parse(group[1]),
                timesRepeat = 0,
                cardNumber = 0,
                balance = income[0].toFloat() + (income[1].toFloat() / 100),
                category = "Из смс"
            )
        )
    }

    private fun processTransferSms(body: String) {
        val matchResult = Regex("[Пп]еревод (\\d+)р").find(body)
        val balance = matchResult?.groupValues?.getOrNull(1)?.toFloatOrNull()

        if (balance != null) {
            AccountRepository.addAccount(
                Account(
                    name = "Перевод",
                    date = LocalDateTime.now(),
                    timesRepeat = 0,
                    cardNumber = 0,
                    balance = balance,
                    category = "Из смс"
                )
            )
        }
    }

    private fun startSmsService(context: Context, body: String) {
        val mIntent = Intent(context, SmsService::class.java).apply {
            putExtra("sms_body", body)
        }
        context.startService(mIntent)
    }

    companion object {
        private const val ACTION = "android.provider.Telephony.SMS_RECEIVED"
    }
}
