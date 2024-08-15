package com.angga.cloudinary.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.angga.cloudinary.R
import com.angga.cloudinary.presentation.ui.theme.CloudinaryTheme

@Composable
fun DeniedPermission(
    modifier: Modifier = Modifier
) {

    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.permission
        )
    )


    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LottieAnimation(
            composition = preloaderLottieComposition,
            modifier = Modifier.size(200.dp),
            iterations = LottieConstants.IterateForever
        )

        Text(
            text = stringResource(R.string.denied_permission),
            textAlign = TextAlign.Center
        )

    }
}

@Preview
@Composable
fun DeniedPermissionPreview() {
    CloudinaryTheme {
        DeniedPermission(
            modifier = Modifier.fillMaxSize()
        )
    }
}