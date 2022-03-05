package com.osisupermoses.healthbloc.navigation

sealed class Screen(val route: String) {
    object NewOrExistingUserScreen: Screen("new_or_existing_user_screen")
    object RegisterUserScreen: Screen("register_user_screen")
    object LoginScreen: Screen("login_screen")
    object OTPScreen: Screen("otp_screen")
    object ConfirmOTPScreen: Screen("confirm_otp_screen")
    object WhoAreYouScreen: Screen("who_are_you_screen")
}
