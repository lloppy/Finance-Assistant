package com.example.compose.rally.data.bill

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

class BillRepository {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        var bills: List<Bill> = listOf(
            Bill(
                "add_bill",
                LocalDateTime.now(),
                0,
                null,
                "Default",
                null,
                0.000001f,
            )
        )

        @RequiresApi(Build.VERSION_CODES.O)
        fun getBill(billName: String?): Bill {
            return bills.first { it.name == billName }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun addBill(bill: Bill, context: Context) {
            if ((bill.name != bills.last().name && bill.amount != bills.last().amount)
                || (bill.category != "QR")
            ) {
                bills += bill
            //    Toast.makeText(context, "Покупка добавлена!", Toast.LENGTH_SHORT).show()
                Log.e("context", "$context")
            }
            //   saveFile(context)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun removeBill(bill: Bill) {
            bills = bills.filterNot {
                it.name == bill.name
                        && it.stringDate == bill.stringDate
                        && it.amount == bill.amount
                        && it.category == bill.category
            }
        }
    }
}
