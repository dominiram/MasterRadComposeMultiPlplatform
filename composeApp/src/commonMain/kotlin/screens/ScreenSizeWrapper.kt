package screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.UiComposable
import androidx.compose.ui.layout.Layout
import models.ScreenSizeModel

@Composable
fun ScreenSizeWrapperLayout(screen: @Composable @UiComposable () -> Unit) {
    val screenSize = remember { mutableStateOf(ScreenSizeModel(-1, -1)) }
    Layout(
        content = screen,
        measurePolicy = { measurables, constraints ->
            // Use the max width and height from the constraints
            val width = constraints.maxWidth
            val height = constraints.maxHeight

            screenSize.value = ScreenSizeModel(width = width, height = height)
            println("Width: $width, height: $height")

            // Measure and place children composables
            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }

            layout(width, height) {
                var yPosition = 0
                placeables.forEach { placeable ->
                    placeable.placeRelative(x = 0, y = yPosition)
                    yPosition += placeable.height
                }
            }
        }
    )
}
