package com.example.compose.rally.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityOptionsCompat
import com.example.compose.rally.R
import com.example.compose.rally.ui.overview.RallyDefaultPadding
import com.example.compose.rally.ui.qr.QRCodeScannerScreen
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton

@Composable
fun addSpend(
    context: Context,
    onAddBillClick: () -> Unit = {}
){
    Row (
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Card(
            modifier = Modifier
                .weight(1f)
                .padding(RallyDefaultPadding)
                .background(colorResource(id = R.color.boxColor)),
        //    elevation = 2.dp,
        ) {
            QRPictureButton(context = context)
        }

        Card(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
        //    elevation = 2.dp,
            ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
            ) {
                TextButton(
                    onClick = { onAddBillClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(color = colorResource(id = R.color.boxColor), shape = RoundedCornerShape(4.dp))
                    //    .border(BorderStroke(2.dp, color = colorResource(id = R.color.boxColor)), shape = RoundedCornerShape(4.dp)),
                    ) {
                    Text(text = "Добавить покупку")
                }

                TextButton(
                    onClick = {  },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(color = colorResource(id = R.color.boxColor), shape = RoundedCornerShape(4.dp))
                    //    .border(BorderStroke(2.dp, color = colorResource(id = R.color.boxColor)), shape = RoundedCornerShape(4.dp)),
                    ) {
                    Text(text = "Загрузить выписку из банка")
                }
            }
        }
    }
}


@Composable
fun QRPictureButton(context: Context){
    Icon(
        imageVector = Icons.Default.QrCode,
        contentDescription = "Camera",
        tint = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .clickable {
                val intent = Intent(context, QRCodeScannerScreen::class.java)
                val options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity)
                context.startActivity(intent, options.toBundle())
            }
    )
    Spacer(
        Modifier
            .width(RallyDefaultPadding)
            .background(colorResource(id = R.color.boxBackground)),
    )
}