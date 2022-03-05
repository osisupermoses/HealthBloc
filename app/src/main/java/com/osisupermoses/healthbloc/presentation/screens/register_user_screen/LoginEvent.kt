package com.osisupermoses.healthbloc.presentation.screens.register_user_screen

sealed class LoginEvent {
    data class EnteredFullName(val value: String) : LoginEvent()
    data class EnteredOrgName(val value: String) : LoginEvent()
    data class EnteredPhoneNum(val value: String) : LoginEvent()
    data class EnteredEmail(val value: String) : LoginEvent()
    data class EnteredPassword(val value: String) : LoginEvent()
    data class OnCheckedChanged(val value: Boolean) : LoginEvent()
    data class PasswordVisibility(val value: Boolean) : LoginEvent()
}
