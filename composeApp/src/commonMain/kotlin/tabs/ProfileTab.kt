package tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import screens.ProfileScreen

data class ProfileTab(private val showBottomNavBar: () -> Unit) : Tab {

    @Composable
    override fun Content() {
        Navigator(screen = ProfileScreen(showBottomNavBar)) { navigator ->
            SlideTransition(navigator = navigator)
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val index: UShort = 2u

            return TabOptions(
                icon = rememberVectorPainter(Icons.Default.Person),
                index = index,
                title = ""
            )
        }
}
