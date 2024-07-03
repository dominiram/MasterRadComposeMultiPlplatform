import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import di.initKoin
import org.jetbrains.compose.ui.tooling.preview.Preview
import tabs.HomeTab
import tabs.ProfileTab
import tabs.SearchTab

@Composable
@Preview
fun App() {
    initKoin()
    var shouldShowBottomNav by remember { mutableStateOf(true) }

    MaterialTheme {
        TabNavigator(
            tab = HomeTab(
                showBottomNavBar = { shouldShowBottomNav = true },
                hideBottomNavBar = { shouldShowBottomNav = false }
            )
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    if (shouldShowBottomNav)
                        BottomNavigation(
                            backgroundColor = Color(0xFF3F54BE)
                        ) {
                            TabNavigationItem(
                                HomeTab(
                                    showBottomNavBar = { shouldShowBottomNav = true },
                                    hideBottomNavBar = { shouldShowBottomNav = false }
                                )
                            )
                            TabNavigationItem(
                                SearchTab(
                                    showBottomNavBar = { shouldShowBottomNav = true },
                                    hideBottomNavBar = { shouldShowBottomNav = false }
                                )
                            )
                            TabNavigationItem(ProfileTab(showBottomNavBar = {
                                shouldShowBottomNav = true
                            }))
                        }
                },
                content = { CurrentTab() }
            )
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator: TabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        modifier = Modifier.padding(top = 24.dp),
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = {
            tab.options.icon?.let { icon ->
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = if (tabNavigator.current == tab) Color.White else Color.Gray
                )
            }
        },
        label = {
            Text(text = tab.options.title)
        }
    )
}
