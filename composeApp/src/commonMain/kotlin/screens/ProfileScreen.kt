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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import kotlinx.coroutines.flow.collectLatest
import masterradcomposemultiplatform.composeapp.generated.resources.Res
import masterradcomposemultiplatform.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import viewModels.ProfileViewModel

class ProfileScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<ProfileViewModel>()
        ProfileScreenRoot(viewModel)
    }
}

@Composable
fun ProfileScreenRoot(viewModel: ProfileViewModel) {
    Column(
        modifier = Modifier.padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var fullName by remember { mutableStateOf("") }
        var jobTitle by remember { mutableStateOf("") }
        var mail by remember { mutableStateOf("") }
        var number by remember { mutableStateOf(0) }

        fullName = viewModel.uiState.collectAsState().value?.name ?: ""
        jobTitle = viewModel.uiState.collectAsState().value?.jobTitle ?: ""
        mail = viewModel.uiState.collectAsState().value?.mail ?: ""
        number = viewModel.uiState.collectAsState().value?.phoneNumber ?: 0

        ProfileIcon()

        ProfileFields(
            fullName = fullName,
            jobTitle = jobTitle,
            mail = mail,
            number = number,
            onNameChanged = { fullName = it },
            onJobTitleChanged = { jobTitle = it },
            onMailChanged = { mail = it },
            onPhoneNumberChanged = { number = it },
        )

        SaveDataButton { viewModel.saveUserData(fullName, jobTitle, mail, number) }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProfileIcon() {
    Box(
        modifier = Modifier.padding(top = 70.dp).height(160.dp).width(160.dp)
    ) {
        //TODO: image url / image from storage instead of Res.drawable...
        Image(
            modifier = Modifier.height(200.dp).width(200.dp),
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = null
        )

        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd).height(45.dp).width(45.dp),
            shape = CircleShape,
            contentColor = Color.Gray,
            containerColor = Color.White,
            onClick = {},
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
