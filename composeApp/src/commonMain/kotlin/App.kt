import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import di.initKoin
import org.jetbrains.compose.ui.tooling.preview.Preview
import screens.HomeScreen

@Composable
@Preview
fun App() {
    initKoin()

    MaterialTheme {
        Navigator(HomeScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }
}