package com.example.compose.rally.data.util.qr

import android.annotation.SuppressLint
import com.example.compose.rally.data.bill.Bill
import java.time.LocalDateTime
import java.util.regex.Matcher
import java.util.regex.Pattern

@SuppressLint("NewApi")
fun createBillFromQR(result: String): Bill {
    val pattern = "t=(\\d{4})(\\d{2})(\\d{2})T(\\d{2})(\\d{2})&s=(.*)\\.(.*)&fn"
    val matcher: Matcher = Pattern.compile(pattern).matcher(result)

    if (matcher.find()) {
        return Bill(
            name = "Покупка " + matcher.group(3) + "." + matcher.group(2),
            date = LocalDateTime.of(
                matcher.group(1)!!.toInt(),
                matcher.group(2)!!.toInt(),
                matcher.group(3)!!.toInt(),
                matcher.group(4)!!.toInt(),
                matcher.group(5)!!.toInt()
            ),
            timesRepeat = 0,
            billPhoto = null,
            category = "QR",
            amount = (matcher.group(6)!!.toFloat()) + (matcher.group(7)!!.toFloat() / 100)
        )
    }
    throw IllegalArgumentException("Invalid QR code format")
}
