package com.osisupermoses.healthbloc.presentation.screens.confirm_otp_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.osisupermoses.healthbloc.R
import com.osisupermoses.healthbloc.navigation.Screen
import com.osisupermoses.healthbloc.presentation.screens.register_user_screen.components.Button
import com.osisupermoses.healthbloc.ui.theme.spacing

@Composable
fun ConfirmOTPScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium),
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = stringResource(R.string.txt_confirm_otp),
            style = MaterialTheme.typography.body1,
            color = Color.Blue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        Text(
            text = stringResource(R.string.txt_otp_confirmed),
            style = MaterialTheme.typography.body2,
            modifier = Modifier.align(Alignment.Center)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
        Button(
            btnColor = ButtonDefaults.buttonColors(Color.Blue),
            textColor = Color.White,
            text = stringResource(R.string.btn_continue)
        ) {
            navController.navigate(Screen.LoginScreen.route)
        }
    }
}