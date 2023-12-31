@file:Suppress("DEPRECATION")

package com.example.compose.rally.ui.overview

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.preference.PreferenceManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.glance.LocalContext
import com.example.compose.rally.R
import com.example.compose.rally.data.analyze.analyzeAlert
import com.example.compose.rally.ui.components.RallyDivider

/**
 * The Alerts card within the Rally Overview screen.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlertCard(
    onClickAnalyze: () -> Unit = {},
    context: Context
) {
    val alertMessage = analyzeAlert()

    Card {
        Column {
            AlertHeader(
                onClickAnalyze = onClickAnalyze
            )
            RallyDivider(
                modifier = Modifier.padding(
                    start = RallyDefaultPadding,
                    end = RallyDefaultPadding
                )
            )
            AlertItem(alertMessage, context)
        }
    }
}

@Composable
private fun AlertHeader(
    onClickAnalyze: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(RallyDefaultPadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.alert_title),
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        TextButton(
            onClick = onClickAnalyze,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = "Анализ моих финансов",
                style = MaterialTheme.typography.button,
            )
        }
    }
}

@Composable
private fun AlertItem(message: String, context: Context) {
    Row(
        modifier = Modifier
            .padding(RallyDefaultPadding)
            // Regard the whole row as one semantics node. This way each row will receive focus as
            // a whole and the focus bounds will be around the whole row content. The semantics
            // properties of the descendants will be merged. If we'd use clearAndSetSemantics instead,
            // we'd have to define the semantics properties explicitly.
            .semantics(mergeDescendants = true) {},
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Spacer(Modifier.height(RallyDefaultPadding))
            Text(
                style = MaterialTheme.typography.body2,
                text = message
            )
            Spacer(Modifier.height(RallyDefaultPadding))
            val currentGoal = readGoalFromSharedPreferences(context) ?: "Не откладывать"

            Text(
                style = MaterialTheme.typography.body2,
                text = "Текущая цель:\n$currentGoal"
            )
            Spacer(Modifier.height(RallyDefaultPadding))
        }
    }
}

@SuppressLint("SuspiciousIndentation")
private fun readGoalFromSharedPreferences(context: Context): String? {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString("current_goal", null)
}