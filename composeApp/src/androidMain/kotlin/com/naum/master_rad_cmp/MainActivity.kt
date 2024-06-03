package com.naum.master_rad_cmp

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import nonShared.ImagePickerFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(ImagePickerFactory().createPicker())
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(ImagePickerFactory().createPicker())
}