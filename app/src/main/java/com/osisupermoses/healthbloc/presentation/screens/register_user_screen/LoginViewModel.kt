package com.osisupermoses.healthbloc.presentation.screens.register_user_screen

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.osisupermoses.healthbloc.domain.model.User
import com.osisupermoses.healthbloc.presentation.screens.register_user_screen.components.Country
import com.osisupermoses.healthbloc.presentation.screens.register_user_screen.components.getCountriesList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val TAG = "LoginViewModel"

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val mAuth = Firebase.auth

    private val _isLoggedIn = mutableStateOf(false)
    val isLoggedIn: State<Boolean> = _isLoggedIn

    private val _state = mutableStateOf(RegisterUserState())
    val state: State<RegisterUserState> = _state

//    private val _verificationOtp = mutableStateOf("")
//    var verificationOtp: State<String> = _verificationOtp


    private val _orgName = mutableStateOf("")
    val orgName: State<String> = _orgName

    private val _fullName = mutableStateOf("")
    val fullName: State<String> = _fullName

    private val _phoneNum = mutableStateOf("")
    val phoneNum: State<String> = _phoneNum

    private val _isChecked = mutableStateOf(false)
    val isChecked: State<Boolean> = _isChecked

//    val countriesList = getCountriesList()
//    val mobileCountry = mutableStateOf(Country("ng", "234", "Nigeria"))

    private val _userEmail = mutableStateOf("")
    val userEmail: State<String> = _userEmail

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    init {
        viewModelScope.launch {
            delay(3000)
            _isLoggedIn.value = getCurrentUser() != null
        }
    }

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.EnteredFullName -> {
                _fullName.value = event.value
            }
            is LoginEvent.EnteredOrgName -> {
                _orgName.value = event.value
            }
            is LoginEvent.EnteredPhoneNum -> {
                _phoneNum.value = event.value
            }
            is LoginEvent.EnteredEmail -> {
                _userEmail.value = event.value
            }
            is LoginEvent.EnteredPassword -> {
                _password.value = event.value
            }
            is LoginEvent.OnCheckedChanged -> {
                _isChecked.value = event.value
            }
            else -> {}
        }
    }

    fun createUserWithEmailAndPassword(OTPScreenNav: () -> Unit) = viewModelScope.launch {
        _state.value = state.value.copy(isLoading = true)
        mAuth.createUserWithEmailAndPassword(userEmail.value, password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    createUser()
                    OTPScreenNav()
                } else {
                    Log.d("FIREBASE", "createUserWithEmailAndPassword: ${task.result.toString()}")
                }
            }
        _state.value = state.value.copy(isLoading = false)
    }

    fun signInWithEmailAndPassword() = viewModelScope.launch {
        try {
            _state.value = state.value.copy(error = "")
            mAuth.signInWithEmailAndPassword(userEmail.value, password.value)
                .addOnCompleteListener { task -> signInCompletedTask(task) }
        } catch (e: Exception) {
            _state.value = state.value.copy(error = e.localizedMessage ?: "Unknown error")
            Log.d(TAG, "Sign in fail: $e")
        }
    }

    fun signInWithGoogleToken(token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        signWithCredential(credential)
    }

    fun signInWithFacebookToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        signWithCredential(credential)
    }

//    fun signInWithTwitter(context: Activity) = viewModelScope.launch {
//        val provider = OAuthProvider.newBuilder("twitter.com")
//
//        // Target specific email with login hint.
//        provider.addCustomParameter("lang", "fr")
//
//        val pendingResultTask: Task<AuthResult> = mAuth.pendingAuthResult!!
//        if (pendingResultTask != null) {
//            pendingResultTask
//                .addOnCompleteListener { task -> signInCompletedTask(task) }
//        } else {
//            Firebase.auth
//                .startActivityForSignInWithProvider(/* activity= */ context, provider.build())
//                .addOnCompleteListener { task -> signInCompletedTask(task) }
//        }
//    }


    private fun signWithCredential(credential: AuthCredential) = viewModelScope.launch {
        try {
            _state.value = state.value.copy(error = "")
            Firebase.auth.signInWithCredential(credential)
                .addOnCompleteListener { task -> signInCompletedTask(task) }
        } catch (e: Exception) {
            _state.value = state.value.copy(error = e.localizedMessage ?: "Unknown error")
        }
    }

    private fun signInCompletedTask(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            Log.d(TAG, "SignInWithEmail:success")
            _userEmail.value = ""
            _password.value = ""
        } else {
            _state.value = state.value.copy(error = task.exception?.localizedMessage ?: "Unknown error")
            // If sign in fails, display a message to the user.
            Log.w(TAG, "SignInWithEmail:failure", task.exception)
        }
        viewModelScope.launch {
            _isLoggedIn.value = getCurrentUser() != null
        }
    }

    private fun getCurrentUser(): FirebaseUser? {
        val user = Firebase.auth.currentUser
        Log.d(TAG, "user display name: ${user?.displayName}, email: ${user?.email}")
        return user
    }

    fun isValidEnteries(): Boolean {
        if (userEmail.value.isBlank() || password.value.isBlank() || fullName.value.isBlank() ||
                orgName.value.isBlank() || !isChecked.value
        ) {
            return false
        }
        return true
    }

    fun signOut() = viewModelScope.launch {
        Firebase.auth.signOut()
        _isLoggedIn.value = false
    }

    private fun createUser() {
        val userId = mAuth.currentUser?.uid
        val user = User(
            id = null,
            userId = userId.toString(),
            fullName = fullName.value,
            profilePhoto = "",
            orgName = orgName.value,
            phoneNum = "",
        ).toMap()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
    }

//    fun send(phoneNumber: String, activity: Activity) {
//        val options = PhoneAuthOptions.newBuilder(mAuth)
//            .setPhoneNumber("+234$phoneNumber")       // Phone number to verify
//            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//            .setActivity(activity)                 // Activity (for callback binding)
//            .setCallbacks(object :
//                PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
//                    _state.value = state.value.copy(isLoading = false)
//                    Log.d("VerificationStatus", "Verification was successful!")
//                    // Toast.makeText(applicationContext, "Verification Completed", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onVerificationFailed(p0: FirebaseException) {
//                    // Toast.makeText(applicationContext, "Verification Failed", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onCodeSent(otp: String, p1: PhoneAuthProvider.ForceResendingToken) {
//                    super.onCodeSent(otp, p1)
//                    _verificationOtp.value = otp
//                    //  Toast.makeText(applicationContext, "Otp Send Successfully", Toast.LENGTH_SHORT).show()
//                }
//            })          // OnVerificationStateChangedCallbacks
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }
}