package com.osisupermoses.healthbloc.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.osisupermoses.healthbloc.presentation.screens.confirm_otp_screen.ConfirmOTPScreen
import com.osisupermoses.healthbloc.presentation.screens.login_screen.LoginScreen
import com.osisupermoses.healthbloc.presentation.screens.new_or_existing_user_screen.NewOrExistingUserScreen
import com.osisupermoses.healthbloc.presentation.screens.otp_screen.OTPScreen
import com.osisupermoses.healthbloc.presentation.screens.register_user_screen.RegisterUserScreen
import com.osisupermoses.healthbloc.presentation.screens.who_are_you_screen.WhoAreYouScreen

@Composable
fun HealthBlocNavigation() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.NewOrExistingUserScreen.route
    ) {
        composable(Screen.NewOrExistingUserScreen.route) {
            NewOrExistingUserScreen(navController)
        }

        composable(Screen.RegisterUserScreen.route) {
            RegisterUserScreen(navController)
        }

        composable(Screen.OTPScreen.route // + "/{mobileNumber}/{otp}"
        ) {
//            val phoneNumber = it.arguments?.getString("mobileNumber")
//            val otp = it.arguments?.getString("otp")
                OTPScreen(navController)
        }
        composable(Screen.ConfirmOTPScreen.route) {
            ConfirmOTPScreen(navController)
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(Screen.WhoAreYouScreen.route) {
            WhoAreYouScreen(navController)
        }
    }
}