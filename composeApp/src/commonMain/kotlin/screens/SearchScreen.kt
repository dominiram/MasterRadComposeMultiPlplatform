package screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen

class SearchScreen : Screen {
    @Composable
    override fun Content() {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start
        ) {
            TitleContainer()
            SearchContainer()
            ArticlesPager()
        }
    }
}

@Composable
fun TitleContainer() {
    Column(modifier = Modifier.fillMaxWidth().padding(top = 76.dp)) {
        Text(
            text = "Discover",
            style = TextStyle(
                fontSize = 32.sp,
                color = Color.Black,
                fontWeight = FontWeight(900)
            )
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = "News from all over the world",
            style = TextStyle(
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight(400)
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContainer() {
    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color.Gray,
        backgroundColor = Color.Gray
    )

    var text by remember { mutableStateOf("") }

    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        BasicTextField(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .background(color = Color(0xFFF2F2F2), shape = RoundedCornerShape(16.dp)),
            value = text,
            onValueChange = {
                text = it
            },
            singleLine = true,
            cursorBrush = SolidColor(Color.Gray),
            textStyle = TextStyle(
                fontSize = 15.sp,
                color = Color.Gray
            ),
            decorationBox = { innerTextField ->
                val interactionSource = remember { MutableInteractionSource() }

                TextFieldDefaults.DecorationBox(
                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 4.dp),
                    value = text,
                    innerTextField = innerTextField,
                    placeholder = {
                        Text(
                            text = "Search",
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 15.sp
                            )
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "",
                            tint = Color.Gray
                        )
                    },
                    enabled = true,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                ) {}
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArticlesPager() {
    val pagerState = rememberPagerState(initialPage = 0) { 5 }
    var selectedTab by remember { mutableIntStateOf(pagerState.currentPage) }

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = selectedTab) {

        }

        HorizontalPager(state = pagerState) {

        }
    }
}
