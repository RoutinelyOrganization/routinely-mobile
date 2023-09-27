package com.example.routinely.splash_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.routinely.R
import com.example.routinely.ui.components.ContinueWithAccountButton
import com.example.routinely.ui.components.GoogleLoginButton
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    val isLogoVisible = remember {
        mutableStateOf(false)
    }
    val isDescriptionTextVisible = remember {
        mutableStateOf(false)
    }
    val isButtonsVisible = remember {
        mutableStateOf(false)
    }
    Column(
    ) {
        Column(
            modifier = Modifier.padding(top = 180.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AnimatedVisibility(
                visible = isLogoVisible.value,
                enter = slideInVertically() + fadeIn()
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.logo_vertical),
                    contentDescription = "Teste",
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                )
            }

            AnimatedVisibility(
                visible = isDescriptionTextVisible.value,
                enter = slideInVertically() + fadeIn()
            ) {
                Text(
                    text = stringResource(R.string.app_description_splash_screen),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(horizontal = 24.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
        AnimatedVisibility(
            visible = isButtonsVisible.value,
            enter = slideInVertically() + fadeIn()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                ContinueWithAccountButton(onClick = {})
                GoogleLoginButton()
            }
        }
    }

    LaunchedEffect(true) {
        delay(500)
        isLogoVisible.value = true
        delay(100)
        isDescriptionTextVisible.value = true
        delay(1000)
        isButtonsVisible.value = true
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(

    )
}