package tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import screens.ProfileScreen
import screens.SearchScreen

object ProfileTab : Tab {
    @Composable
    override fun Content() {
        Navigator(screen = ProfileScreen()) { navigator ->
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