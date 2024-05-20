package utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun AsyncImage(modifier: Modifier, imageUrl: String, height: Int, width: Int) {
    KamelImage(
        modifier = modifier
            .height(height.dp).width(width.dp),
        resource = asyncPainterResource(imageUrl.replaceImageWidthAndHeight(height, width)),
        contentDescription = null
    )
}

@Composable
fun RoundedCornerAsyncImage(modifier: Modifier, imageUrl: String, height: Int, width: Int) {
    KamelImage(
        modifier = modifier
            .height(height.dp).width(width.dp)
            .clip(shape = RoundedCornerShape(16.dp)),
        resource = asyncPainterResource(imageUrl.replaceImageWidthAndHeight(height, width)),
        contentDescription = null
    )
}

fun String.replaceImageWidthAndHeight(height: Int, width: Int) =
    this.replace("[height]", "$height").replace("[width]", "$width")
