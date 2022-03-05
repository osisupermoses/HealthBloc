package com.osisupermoses.healthbloc.presentation.screens.login_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.osisupermoses.healthbloc.presentation.screens.register_user_screen.LoginViewModel
import com.osisupermoses.healthbloc.presentation.screens.register_user_screen.components.HealthBlocTopAppBar
import com.osisupermoses.healthbloc.ui.theme.spacing

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            HealthBlocTopAppBar(navController = navController) {
                navController.popBackStack()
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
                .fillMaxWidth()
        ) {
//            Text(text = )
        }
    }
}