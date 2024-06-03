import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import nonShared.ImagePickerFactory

fun MainViewController() =
    ComposeUIViewController { App(ImagePickerFactory(LocalUIViewController.current).createPicker()) }