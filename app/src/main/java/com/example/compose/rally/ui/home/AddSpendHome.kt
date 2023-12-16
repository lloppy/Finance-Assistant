package com.example.compose.rally.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityOptionsCompat
import androidx.core.app.ComponentActivity
import com.example.compose.rally.R
import com.example.compose.rally.data.util.csv.handleCSVFile
import com.example.compose.rally.ui.overview.RallyDefaultPadding
import com.example.compose.rally.ui.qr.QRCodeScannerScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun addSpend(
    context: Context,
    onAddBillClick: () -> Unit = {},
) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    handleCSVFile(context, uri)
                }
            }
        }
    Row(
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
                        .background(
                            color = colorResource(id = R.color.boxColor),
                            shape = RoundedCornerShape(4.dp)
                        )
                    //    .border(BorderStroke(2.dp, color = colorResource(id = R.color.boxColor)), shape = RoundedCornerShape(4.dp)),
                ) {
                    Text(
                        text = stringResource(R.string.add_spend),
                        color = Color.White
                    )
                }
                TextButton(
                    onClick = {
                        launcher.launch(createChooseFileIntent())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(
                            color = colorResource(id = R.color.boxColor),
                            shape = RoundedCornerShape(4.dp)
                        )
                    //    .border(BorderStroke(2.dp, color = colorResource(id = R.color.boxColor)), shape = RoundedCornerShape(4.dp)),
                ) {
                    Text(
                        text = stringResource(R.string.load_bank_data),
                        color = Color.White
                    )
                }
            }
        }
    }
}


@Composable
fun QRPictureButton(context: Context) {
    Image(
        painter = painterResource(id = R.drawable.enderscan),
        contentDescription = "Camera",
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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


private fun createChooseFileIntent(): Intent {
    val intent = Intent(Intent.ACTION_GET_CONTENT)
    intent.type = "text/csv"

    return intent
}