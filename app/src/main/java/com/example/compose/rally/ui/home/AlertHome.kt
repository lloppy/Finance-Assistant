package com.example.compose.rally.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityOptionsCompat
import com.example.compose.rally.R
import com.example.compose.rally.ui.overview.AlertCard
import com.example.compose.rally.ui.overview.RallyDefaultPadding
import com.example.compose.rally.ui.qr.QRCodeScannerScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlertHome(onClickAnalyze: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.boxColor)),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Spacer(
            modifier = Modifier
                .width(RallyDefaultPadding)
                .background(colorResource(id = R.color.boxBackground)),
        )

        AlertCard(onClickAnalyze)

    }

}