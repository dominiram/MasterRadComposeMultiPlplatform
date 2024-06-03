package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import masterradcomposemultiplatform.composeapp.generated.resources.Res
import masterradcomposemultiplatform.composeapp.generated.resources.compose_multiplatform
import nonShared.ImagePicker
import nonShared.rememberBitmapFromBytes
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import viewModels.ProfileViewModel

class ProfileScreen(private val imagePicker: ImagePicker) : Screen {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<ProfileViewModel>()
        imagePicker.RegisterPicker { imageBytes -> viewModel.saveUserImage(imageBytes) }
        ProfileScreenRoot(viewModel) { imagePicker.ShowImagePicker() }
    }
}

@Composable
fun ProfileScreenRoot(viewModel: ProfileViewModel, onImagePicked: () -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val photoBytes = viewModel.uiState.collectAsState().value?.userImage
        val fullName = viewModel.uiState.collectAsState().value?.name ?: ""
        val jobTitle = viewModel.uiState.collectAsState().value?.jobTitle ?: ""
        val mail = viewModel.uiState.collectAsState().value?.mail ?: ""
        val number = viewModel.uiState.collectAsState().value?.phoneNumber ?: 0

        ProfileIcon(photoBytes) { onImagePicked() }

        ProfileFields(
            fullName = fullName,
            jobTitle = jobTitle,
            mail = mail,
            number = number,
            onNameChanged = { viewModel.setFields(name = it) },
            onJobTitleChanged = { viewModel.setFields(jobTitle = it) },
            onMailChanged = { viewModel.setFields(mail = it) },
            onPhoneNumberChanged = { viewModel.setFields(phoneNumber = it) },
        )

        SaveDataButton { viewModel.saveUserData() }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProfileIcon(photoBytes: ByteArray?, openImagePicker: () -> Unit) {
    Box(
        modifier = Modifier.padding(top = 70.dp).height(160.dp).width(160.dp)
    ) {

        rememberBitmapFromBytes(photoBytes)?.let {
            Image(
                modifier = Modifier.height(200.dp).width(200.dp),
                bitmap = it,
                contentDescription = null
            )
        } ?: Image(
            modifier = Modifier.height(200.dp).width(200.dp),
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = null
        )

        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd).height(45.dp).width(45.dp),
            shape = CircleShape,
            contentColor = Color.Gray,
            containerColor = Color.White,
            onClick = { openImagePicker() },
        ) {
            Icon(
                modifier = Modifier,
                imageVector = Icons.Default.Edit,
                contentDescription = null
            )
        }
    }
}

@Composable
fun ProfileFields(
    fullName: String,
    jobTitle: String,
    mail: String,
    number: Int,
    onNameChanged: (String) -> Unit,
    onJobTitleChanged: (String) -> Unit,
    onMailChanged: (String) -> Unit,
    onPhoneNumberChanged: (Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(top = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.Start
    ) {
        EditTextWithLabelAbove(
            label = "Full name",
            textFieldValue = fullName,
            onTextFieldValueChanged = { onNameChanged(it) },
        )

        EditTextWithLabelAbove(
            label = "Job title",
            textFieldValue = jobTitle,
            onTextFieldValueChanged = { onJobTitleChanged(it) },
        )

        EditTextWithLabelAbove(
            label = "E-mail",
            textFieldValue = mail,
            onTextFieldValueChanged = { onMailChanged(it) },
        )

        EditTextWithLabelAbove(
            label = "Phone number",
            textFieldValue = number.toString(),
            onTextFieldValueChanged = { onPhoneNumberChanged(it.toIntOrNull() ?: 0) },
        )
    }
}

@Composable
fun EditTextWithLabelAbove(
    label: String,
    textFieldValue: String,
    onTextFieldValueChanged: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            style = TextStyle(
                color = Color.LightGray,
                fontSize = 14.sp
            )
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = textFieldValue,
            onValueChange = { onTextFieldValueChanged(it) },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 26.sp,
            ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Gray,
                unfocusedIndicatorColor = Color.Gray
            )
        )
    }
}

@Composable
fun SaveDataButton(saveUserData: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 40.dp)
            .background(color = Color.Gray, shape = RoundedCornerShape(8)),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Gray,
            contentColor = Color.Gray
        ),
        onClick = { saveUserData() }
    ) {
        Text(
            text = "Save user data",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight(500),
                color = Color.Black
            )
        )
    }
}
