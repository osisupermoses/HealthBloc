package com.osisupermoses.healthbloc.presentation.screens.splash_screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

//@Composable
//fun SplashScreen() {
//    val scale = remember {
//        Animatable(0f)
//    }
//    LaunchedEffect(key1 = true) {
//        scale.animateTo(
//            targetValue = 0.9f,
//            animationSpec = tween(
//                durationMillis = 800,
//                easing = { OvershootInterpolator(8f).getInterpolation(it) }
//            )
//        )
////        delay(2000L)
//
////        //checks if there's a Firebase User, if so it then takes them to HomeScreen, otherwise to LoginScreen
////        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
////            navController.navigate(ReaderScreens.LoginScreen.name)
////        } else {
////            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
////        }
//    }
//
//    Box(
//        modifier = Modifier
//            .background(Color.Blue)
//    ) {
//        Column(modifier = Modifier.padding(1.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            ReaderLogo()
//            Spacer(modifier = Modifier.height(15.dp))
//            Text(
//                text = " \"Reader. Change. Yourself \"",
//                style = MaterialTheme.typography.h5,
//                color = Color.LightGray
//            )
//        }
//    }
//}