package com.example.compose.rally.data.bill

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import kotlin.math.ceil

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
                var name = bill.name
                var counter = 0

                while (bills.any { it.name == name }) {
                    name = "$name ${++counter}"
                }

                when (bill.timesRepeat) {
                    0 -> {
                        bills += bill.copy(name = name)
                    }

                    1 -> {
                        for (n in 0..30 - bill.date.dayOfMonth) {
                            while (bills.any { it.name == name }) {
                                name = "$name ${++counter}"
                            }
                            bills += Bill(
                                name = name,
                                date = bill.date.plusDays(n.toLong()),
                                timesRepeat = 0,
                                amount = bill.amount,
                                category = bill.category,
                                billPhoto = bill.billPhoto,
                                mcc = bill.mcc
                            )
                        }
                    }

                    2 -> {
                        for (n in 0..ceil((30 - bill.date.dayOfMonth) / 7.0).toInt()) {
                            while (bills.any { it.name == name }) {
                                name = "$name ${++counter}"
                            }
                            bills += Bill(
                                name = name,
                                date = bill.date.plusWeeks(n.toLong()),
                                timesRepeat = 0,
                                amount = bill.amount,
                                category = bill.category,
                                billPhoto = bill.billPhoto,
                                mcc = bill.mcc
                            )
                        }
                    }

                    3 -> {
                        for (n in 0 until ceil((30 - bill.date.dayOfMonth) / 30.0).toInt()) {
                            while (bills.any { it.name == name }) {
                                name = "$name ${++counter}"
                            }
                            bills += Bill(
                                name = name,
                                date = bill.date.plusMonths(n.toLong()),
                                timesRepeat = 0,
                                amount = bill.amount,
                                category = bill.category,
                                billPhoto = bill.billPhoto,
                                mcc = bill.mcc
                            )
                        }
                        Log.e("context", "$context")
                    }
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun removeBill(bill: Bill) {
            bills = bills.filterNot {
                it.name == bill.name
                        && it.stringDate == bill.stringDate
                        && it.timesRepeat == bill.timesRepeat
                        && it.category == bill.category
                        && it.mcc == bill.mcc
                        && it.amount == bill.amount
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getAllBills(): List<Bill> {
            return bills
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun setFileBills(billsList: List<Bill>) {
            bills = billsList
        }
    }
}
