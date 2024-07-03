package tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import screens.SearchScreen

data class SearchTab(
    private val showBottomNavBar: () -> Unit,
    private val hideBottomNavBar: () -> Unit
) : Tab {
    @Composable
    override fun Content() {
        Navigator(screen = SearchScreen(showBottomNavBar, hideBottomNavBar)) { navigator ->
            SlideTransition(navigator = navigator)
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val index: UShort = 1u

            return TabOptions(
                icon = rememberVectorPainter(Icons.Default.Search),
                index = index,
                title = ""
            )
        }
}
