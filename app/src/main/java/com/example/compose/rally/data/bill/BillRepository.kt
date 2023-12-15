package com.example.compose.rally.data.bill

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.compose.rally.data.account.AccountRepository
import com.example.compose.rally.data.account.AccountRepository.Companion.accounts
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
            return try {
                bills.first { it.name == billName }
            } catch (e: NoSuchElementException) {
                bills[1]
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun addBill(bill: Bill, context: Context) {
            if ((bill.name != bills.last().name && bill.amount != bills.last().amount)
                || (bill.category != "QR")
            ) {
                var name = bill.name
                var counter = 0

                while (bills.any { it.name == name }) {
                    name = "$name ${++counter}"
                }
                bills += bill.copy(name = name)

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

        @RequiresApi(Build.VERSION_CODES.O)
        fun getAllBills(): List<Bill> {
            return bills
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun setFileBills(billsList: List<Bill>) {
            BillRepository.bills = billsList
        }
    }
}
