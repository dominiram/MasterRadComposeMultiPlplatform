package nonShared

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

actual class ImagePicker(private val activity: ComponentActivity) {

    private lateinit var getContent: ActivityResultLauncher<String>

    @Composable
    actual fun RegisterPicker(onImagePicked: (ByteArray) -> Unit) {
        getContent =
            activity.registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
                    activity.contentResolver.openInputStream(uri)?.use {
                        onImagePicked(it.readBytes())
                    }
                }
            }
    }

    actual fun ShowImagePicker() {
        getContent.launch("image/*")
    }
}
