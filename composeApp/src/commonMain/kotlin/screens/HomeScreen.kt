package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.AsyncImage
import masterradcomposemultiplatform.composeapp.generated.resources.Res
import masterradcomposemultiplatform.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
        HomeScreenImage("https://picsum.photos/200")
    }
}

@Composable
fun HomeScreenImage(imageUrl: String) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth().clip(shape = RoundedCornerShape(16.dp))) {
        val (image, imageText) = createRefs()

        AsyncImage(
            modifier = Modifier.fillMaxSize().constrainAs(image) {},
            model = imageUrl,
            contentDescription = null
        )

        Column(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .constrainAs(imageText) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                },
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.LightGray
                    )
                    .padding(8.dp),
                text = "News of the Day",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.White
                )
            )

            Text(
                modifier = Modifier.padding(4.dp),
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras molestie maximus",
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight(700)
                )
            )

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = "Learn more",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.White
                    )
                )

                Icon(
                    modifier = Modifier.background(color = Color.White),
                    imageVector = Icons.AutoMirrored.Default.ArrowForward,
                    contentDescription = null
                )
            }
        }
    }
}
