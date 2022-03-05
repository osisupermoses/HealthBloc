package com.osisupermoses.healthbloc.presentation.screens.register_user_screen

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.osisupermoses.healthbloc.R
import com.osisupermoses.healthbloc.navigation.Screen
import com.osisupermoses.healthbloc.presentation.screens.getActivity
import com.osisupermoses.healthbloc.presentation.screens.register_user_screen.components.*
import com.osisupermoses.healthbloc.ui.theme.spacing

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterUserScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    val state = loginViewModel.state.value
    val phoneNum = loginViewModel.phoneNum
//    val verifcationOtp = loginViewModel.verificationOtp
    val fullName = loginViewModel.fullName
    val orgName = loginViewModel.orgName
//    val countriesList = loginViewModel.countriesList
//    val mobileCountry = loginViewModel.mobileCountry
    val email = loginViewModel.userEmail
    val password = loginViewModel.password
    val isChecked = loginViewModel.isChecked
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current

     Scaffold(
        topBar = {
            HealthBlocTopAppBar(navController = navController) {
                navController.navigate(Screen.NewOrExistingUserScreen.route)
            }
        },
        scaffoldState = scaffoldState
     ) {
         Column(
             modifier = Modifier
                 .fillMaxSize()
                 .padding(MaterialTheme.spacing.medium)
                 .verticalScroll(scrollState)
         ) {
             Text(
                text = stringResource(R.string.txt_intro_note),
                style = MaterialTheme.typography.body2,
                fontSize = 15.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = MaterialTheme.spacing.extraLarge)
            )
             Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

             TextField(
                 value = fullName.value,
                 placeholderText = stringResource(R.string.txt_full_name),
                 icon = Icons.Default.Person,
                 text = stringResource(R.string.txt_full_name)
             ) { loginViewModel.onEvent(LoginEvent.EnteredFullName(it)) }
             Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
             TextField(
                 value = orgName.value,
                 placeholderText = stringResource(R.string.txt_org_name),
                 icon = Icons.Default.Work,
                 text = stringResource(R.string.txt_org_name)
             ) { loginViewModel.onEvent(LoginEvent.EnteredOrgName(it)) }
             Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
             EmailInput(
                 email = email.value,
                 onAction = KeyboardActions { passwordFocusRequest.requestFocus() }
             ) { loginViewModel.onEvent(LoginEvent.EnteredEmail(it))}
             Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
             PasswordInput(
                 modifier = Modifier.focusRequester(passwordFocusRequest),
                 password = password.value,
//                 onAction = KeyboardActions {
//                     if (!valid) return@KeyboardActions
//                     email.value.trim()
//                     password.value.trim()
//                 }
             ) { loginViewModel.onEvent(LoginEvent.EnteredPassword(it)) }
             CheckedBoxWithText(isChecked.value) {
                 loginViewModel.onEvent(LoginEvent.OnCheckedChanged(it))
             }
             Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
             Button(
                 btnColor = ButtonDefaults.buttonColors(Color.Blue),
                 textColor = Color.White,
                 enabled = loginViewModel.isValidEnteries(),
                 text = stringResource(R.string.btn_get_started)
             ) {
                 keyboardController?.hide()
                 loginViewModel.createUserWithEmailAndPassword {
                     navController.navigate(Screen.OTPScreen.route)
                 }

             }
             if (state.error.isNotBlank()) {
                 Text(
                     text = state.error,
                     color = MaterialTheme.colors.error,
                     textAlign = TextAlign.Center,
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding(horizontal = 20.dp)
                         .align(Alignment.CenterHorizontally)
                 )
             }
             if (state.isLoading) {
                 CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
             }
         }
     }
}

@Composable
@Preview(showBackground = true)
fun RegisterUserScreenPreview() {
    val navController = rememberNavController()
    RegisterUserScreen(navController = navController)
}