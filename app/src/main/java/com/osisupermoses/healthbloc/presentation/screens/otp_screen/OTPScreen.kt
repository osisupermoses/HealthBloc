package com.osisupermoses.healthbloc.presentation.screens.otp_screen

import android.view.Gravity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.osisupermoses.healthbloc.R
import com.osisupermoses.healthbloc.navigation.Screen
import com.osisupermoses.healthbloc.presentation.screens.confirm_otp_screen.ConfirmOTPScreen
import com.osisupermoses.healthbloc.presentation.screens.getActivity
import com.osisupermoses.healthbloc.presentation.screens.register_user_screen.components.Button
import com.osisupermoses.healthbloc.presentation.screens.register_user_screen.components.HealthBlocTopAppBar
import com.osisupermoses.healthbloc.presentation.screens.register_user_screen.components.PhoneNumberTextField
import com.osisupermoses.healthbloc.ui.theme.Purple500
import com.osisupermoses.healthbloc.ui.theme.spacing


@Composable
fun OTPScreen(
    navController: NavController,
    OTPViewModel: OTPViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var otpVal: String? = null
    val customView = remember { LottieAnimationView(context) }
    val phoneNum = OTPViewModel.phoneNumber
    val countriesList = OTPViewModel.countriesList
    val mobileCountry = OTPViewModel.mobileCountry
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            HealthBlocTopAppBar(navController = navController) {}
        }
    ) {
        Column(
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //Add Lottie file
            AndroidView(
                { customView },
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
            ) { view ->
                with(view) {
                    setAnimation(R.raw.phone_number_verify)
                    playAnimation()
                    repeatCount = LottieDrawable.INFINITE
                    foregroundGravity = Gravity.CENTER
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))

            PhoneNumberTextField(
                phoneNum.value,
                mobileCountry = mobileCountry.value,
                onCountrySelected = { selectedCountry ->
                    mobileCountry.value = selectedCountry
                },
                countriesList = countriesList
            ) {
                OTPViewModel.setPhoneNum(it)
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            Button(
                onClick = {
                    context.getActivity()?.let { it ->
                        OTPViewModel.send(
                            "+${mobileCountry.value.code}${phoneNum.value}", it
                        )
                    } // Here pass the phone number value only
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(45.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Purple500)
            ) {
                Text(text = "Send Otp", fontSize = 15.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))

            Text(
                text = stringResource(R.string.txt_please_enter_otp),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.txt_code_was_sent_to) + " " + "+${mobileCountry.value.code}${phoneNum.value}",
                    style = MaterialTheme.typography.body2,
                    color = Color.LightGray,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(MaterialTheme.spacing.extraSmall)
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                Text(
                    text = stringResource(R.string.txt_edit),
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Medium,
                    color = Color.Red
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.txt_code_expires_in),
                    style = MaterialTheme.typography.body2,
                    color = Color.LightGray,
                    modifier = Modifier.padding(MaterialTheme.spacing.extraSmall)
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.extraSmall))
                Text(
                    text = "03:48",
                    style = MaterialTheme.typography.body2,
                    color = Color.Blue
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            OTPTextFields(
                length = 6
            ) { getOpt ->
                otpVal = getOpt
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.txt_didnt_receive_otp),
                    style = MaterialTheme.typography.body2,
                    color = Color.LightGray
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
                Row(
                    modifier = Modifier.clickable {
                        context.getActivity()?.let { it ->
                            OTPViewModel.send(
                                "+${mobileCountry.value.code}${phoneNum.value}", it
                            )
                            otpVal = ""
                        }
                },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Restore,
                        contentDescription = "Resend",
                        tint = Color.Red
                    )
                    Text(
                        text = " " + stringResource(R.string.txt_resend),
                        style = MaterialTheme.typography.body2,
                        color = Color.Red
                    )
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            Button(
                btnColor = ButtonDefaults.buttonColors(Purple500),
                textColor = Color.White,
                text = stringResource(R.string.btn_continue)
            ) {
                if (!otpVal.isNullOrEmpty()) {
                    OTPViewModel.otpVerification(otpVal!!) {
                        navController.navigate(Screen.ConfirmOTPScreen.route)
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun Preview() {
    val navController = rememberNavController()
    OTPScreen(navController = navController)
}