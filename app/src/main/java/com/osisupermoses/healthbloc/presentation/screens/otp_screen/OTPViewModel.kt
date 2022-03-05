package com.osisupermoses.healthbloc.presentation.screens.otp_screen

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.osisupermoses.healthbloc.presentation.screens.register_user_screen.components.Country
import com.osisupermoses.healthbloc.presentation.screens.register_user_screen.components.getCountriesList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class OTPViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mAuth = Firebase.auth

    private val TAG = "OTPSignIn"

    private val _state = mutableStateOf(OTPScreenState())
    val state: State<OTPScreenState> = _state

    private val _phoneNumber = mutableStateOf("")
    val phoneNumber: State<String> = _phoneNumber

    private val _verificationOtp = mutableStateOf("")
    var verificationOtp: State<String> = _verificationOtp

    val countriesList = getCountriesList()

    val mobileCountry = mutableStateOf(Country("ng", "234", "Nigeria"))

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun setPhoneNum(value: String) {
        _phoneNumber.value = value
    }

    fun send(phoneNumber: String, activity: Activity) {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity)                 // Activity (for callback binding)
            .setCallbacks(object :
                PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    // Toast.makeText(applicationContext, "Verification Completed", Toast.LENGTH_SHORT).show()
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    // Toast.makeText(applicationContext, "Verification Failed", Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(otp: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(otp, p1)
                    _verificationOtp.value = otp
                   //  Toast.makeText(applicationContext, "Otp Send Successfully", Toast.LENGTH_SHORT).show()
                }
            })          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun otpVerification(otp: String, navToConfirmOTPScreen: () -> Unit) {
        val credential = PhoneAuthProvider.getCredential(verificationOtp.value, otp)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    navToConfirmOTPScreen()
                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                        _state.value = state.value.copy(error = task.exception?.message ?: "Sign in failed")
                    Log.w(TAG, "signInWithCredential:failure1", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        val e = "The verification code entered was invalid"
                        _state.value = state.value.copy(error = e)
                        Log.w(TAG, "signInWithCredential:failure2: $e" )
                    }
                    // Update UI
                }
            }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }
}

