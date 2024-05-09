package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
    ) {
        HomeScreenImage(imageUrl = "https://images.unsplash.com/photo-1656106534627-0fef76c8b042?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=450&h=420")
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Breaking News",
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Color.Black,
                    fontWeight = FontWeight(700)
                )
            )

            Text(
                text = "More",
                style = TextStyle(
                    fontSize = 15.sp,
                    color = Color.Black
                )
            )
        }
    }
}

@Composable
fun HomeScreenImage(imageUrl: String) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (image, imageText) = createRefs()

        KamelImage(
            modifier = Modifier
                .padding(top = 50.dp)
                .height(400.dp)
                .fillMaxWidth()
                .constrainAs(image) {},
            resource = asyncPainterResource(imageUrl),
            contentDescription = null
        )

        Column(
            modifier = Modifier
                .padding(bottom = 32.dp, start = 16.dp)
                .constrainAs(imageText) {
                    bottom.linkTo(image.bottom)
                    start.linkTo(parent.start)
                },
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.Gray.copy(alpha = 0.6f)
                    )
                    .padding(12.dp),
                text = "News of the Day",
                style = TextStyle(
                    fontSize = 13.sp,
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
                        fontSize = 14.sp,
                        color = Color.White
                    )
                )

                Icon(
                    modifier = Modifier,
                    tint = Color.White,
                    imageVector = Icons.AutoMirrored.Default.ArrowForward,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun BreakingNewsList() {
    LazyRow(modifier = Modifier.padding(horizontal = 12.dp)) {

    }
}
