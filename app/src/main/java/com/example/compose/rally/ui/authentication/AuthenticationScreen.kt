@file:Suppress("DEPRECATION")

package com.example.compose.rally.ui.authentication

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.compose.rally.R

@Composable
fun AuthenticationScreen(
    onAuthenticationSuccess: () -> Unit,
    onAuthenticationFailed: () -> Unit
) {
    var fingerprintEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Depending on your needs, you can use either a password field or a fingerprint icon here.
        if (fingerprintEnabled) {
            FingerprintAuthentication(
                onAuthenticationSuccess = onAuthenticationSuccess,
                onAuthenticationFailed = onAuthenticationFailed
            )
        }
    }
}

@Composable
fun FingerprintAuthentication(
    onAuthenticationSuccess: () -> Unit,
    onAuthenticationFailed: () -> Unit
) {
    val context = LocalContext.current
    val executor = ContextCompat.getMainExecutor(context)
    val biometricPromptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle(stringResource(R.string.fingerprint_title))
        .setSubtitle(stringResource(R.string.fingerprint_subtitle))
        .setNegativeButtonText(stringResource(R.string.fingerprint_cancel))
        .build()

    DisposableEffect(context) {
        val biometricManager = BiometricManager.from(context)
        val canAuthenticate = biometricManager.canAuthenticate()

        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            val biometricPrompt = BiometricPrompt(context as FragmentActivity, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        onAuthenticationSuccess()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        onAuthenticationFailed()
                    }
                })

            biometricPrompt.authenticate(biometricPromptInfo)
        } else {
            // Fingerprint authentication not available or not enrolled
            onAuthenticationFailed()
        }

        onDispose {
            // Cleanup if needed
        }
    }
}

