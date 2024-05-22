package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
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
        ProfileIcon()
        ProfileFields()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProfileIcon() {
    Column(
        modifier = Modifier.height(160.dp).width(160.dp)
    ) {
        //TODO: image url / image from storage instead of Res.drawable...
        Image(
            modifier = Modifier.height(150.dp).width(150.dp),
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = null
        )

        //TODO: Replace this with FloatingActionButton
        Icon(
            modifier = Modifier.align(Alignment.End).height(50.dp).width(50.dp),
            imageVector = Icons.Default.Edit,
            contentDescription = null
        )
    }
}

@Composable
fun ProfileFields() {
    var fullName by remember { mutableStateOf("") }
    var headline by remember { mutableStateOf("") }
    var mail by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.Start
    ) {
        EditTextWithLabelAbove(
            label = "Full name",
            textFieldValue = fullName,
            onTextFieldValueChanged = { fullName = it},
        )

        EditTextWithLabelAbove(
            label = "Job title",
            textFieldValue = headline,
            onTextFieldValueChanged = { headline = it },
        )

        EditTextWithLabelAbove(
            label = "E-mail",
            textFieldValue = mail,
            onTextFieldValueChanged = { mail = it },
        )

        EditTextWithLabelAbove(
            label = "Phone number",
            textFieldValue = number,
            onTextFieldValueChanged = { number = it },
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
                fontSize = 12.sp
            )
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = textFieldValue,
            onValueChange = { onTextFieldValueChanged(it) },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 17.sp,
            ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
    }
}
