package tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import screens.HomeScreen

data class HomeTab(
    private val hideBottomNavBar: () -> Unit,
    private val showBottomNavBar: () -> Unit,
) : Tab {
    @Composable
    override fun Content() {
        Navigator(
            screen = HomeScreen(
                showBottomNavBar = showBottomNavBar,
                hideBottomNavBar = hideBottomNavBar
            )
        ) { navigator ->
            SlideTransition(navigator = navigator)
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val index: UShort = 0u

            return TabOptions(
                icon = rememberVectorPainter(Icons.Default.Home),
                index = index,
                title = ""
            )
        }
}
