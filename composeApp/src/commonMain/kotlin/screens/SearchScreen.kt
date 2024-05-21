package screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import kotlinx.coroutines.launch
import masterradcomposemultiplatform.composeapp.generated.resources.Res
import masterradcomposemultiplatform.composeapp.generated.resources.compose_multiplatform
import masterradcomposemultiplatform.composeapp.generated.resources.ic_schedule
import masterradcomposemultiplatform.composeapp.generated.resources.ic_visibility
import models.ArticleModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import utils.RoundedCornerAsyncImage
import viewModels.HomeViewModel

class SearchScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<HomeViewModel>()

        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start
        ) {
            TitleContainer()
            SearchContainer()
            ArticlesPager(viewModel.getArticles())
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
                .padding(top = 12.dp)
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
fun ArticlesPager(articles: List<ArticleModel>) {
    val pagerState = rememberPagerState(initialPage = 0) { 5 }
    var selectedTab by remember { mutableIntStateOf(pagerState.currentPage) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth()) {
        ScrollableTabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .fillMaxWidth(),
                    color = Color.Black,
                    height = 2.dp
                )
            },
            divider = {}
        ) {
            for (i in 0 until pagerState.pageCount) {
                Tab(
                    modifier = Modifier.wrapContentSize().padding(8.dp),
                    selected = i == selectedTab,
                    onClick = {
                        selectedTab = i
                        coroutineScope.launch { pagerState.animateScrollToPage(selectedTab) }
                    }
                ) {
                    Text(
                        text = getTextForTab(i),
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 24.sp,
                            fontWeight = FontWeight(600)
                        )
                    )
                }
            }
        }

        HorizontalPager(state = pagerState) {
            TabPage(articles)
        }
    }
}

@Composable
fun TabPage(articles: List<ArticleModel>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top)
    ) {
        items(
            items = articles,
            key = { it.id }
        ) {
            ArticleColumnItem(it)
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ArticleColumnItem(article: ArticleModel) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundedCornerAsyncImage(
            modifier = Modifier,
            imageUrl = article.imageUrl,
            width = 80,
            height = 80
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = article.title,
                style = TextStyle(
                    fontSize = 13.sp,
                    color = Color.Black,
                    letterSpacing = 0.24.sp,
                    fontWeight = FontWeight(700)
                ),
                maxLines = 2
            )

            Row(
                modifier = Modifier.padding(start = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(space = 0.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.height(16.dp).width(16.dp),
                    painter = painterResource(Res.drawable.ic_schedule),
                    tint = Color.DarkGray,
                    contentDescription = null
                )

                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = article.createdAt,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    ),
                    maxLines = 1
                )

                Icon(
                    modifier = Modifier.padding(start = 24.dp).height(18.dp).width(18.dp),
                    painter = painterResource(Res.drawable.ic_visibility),
                    tint = Color.DarkGray,
                    contentDescription = null
                )

                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "${article.views} views",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    ),
                    maxLines = 1
                )
            }
        }
    }
}

fun getTextForTab(index: Int) = when (index) {
    0 -> "Health"
    1 -> "Politics"
    2 -> "Art"
    3 -> "Food"
    else -> "Science"
}
