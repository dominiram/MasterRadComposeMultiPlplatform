package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import models.ArticleModel
import viewModels.ArticleViewModel

data class ArticleScreen(private val articleId: Int, private val hideBottomNavBar: () -> Unit) :
    Screen {
    @Composable
    override fun Content() {
        hideBottomNavBar()
        val navigator = LocalNavigator.current
        val viewModel = getScreenModel<ArticleViewModel>()
        val article = viewModel.getArticle(articleId)
        ArticleScreenRoot(navigator, article)
    }
}

@Composable
fun ArticleScreenRoot(navigator: Navigator?, article: ArticleModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        ArticleImageScreen(
            article = article,
            navigateBack = { navigator?.pop() }
        )

        ArticleBottomInfoModal(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun ArticleImageScreen(article: ArticleModel, navigateBack: () -> Unit) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (image, imageText, backArrow) = createRefs()

        KamelImage(
            modifier = Modifier
                .background(color = Color.Black)
                .height(460.dp)
                .fillMaxWidth()
                .constrainAs(image) {
                    top.linkTo(parent.top)
                },
            resource = asyncPainterResource(article.coverImageUrl),
            contentDescription = null
        )

        Icon(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
                .height(24.dp)
                .width(24.dp)
                .clickable {
                    navigateBack()
                }
                .constrainAs(backArrow) {
                    top.linkTo(image.top)
                    start.linkTo(image.start)
                },
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "",
            tint = Color.White
        )

        Column(
            modifier = Modifier
                .padding(bottom = 32.dp, start = 16.dp, end = 16.dp)
                .constrainAs(imageText) {
                    bottom.linkTo(image.bottom)
                    start.linkTo(parent.start)
                },
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.Gray.copy(alpha = 0.6f)
                    )
                    .padding(12.dp),
                text = "Politics",
                style = TextStyle(
                    fontSize = 13.sp,
                    color = Color.White
                )
            )

            Text(
                modifier = Modifier.padding(4.dp),
                text = article.title,
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight(700)
                )
            )

            Text(
                modifier = Modifier.padding(4.dp),
                text = article.subtitle,
                style = TextStyle(
                    fontSize = 13.sp,
                    color = Color.White
                )
            )
        }
    }
}

@Composable
fun ArticleBottomInfoModal(modifier: Modifier) {
    Column(modifier = modifier.clip(shape = RoundedCornerShape(16.dp))) {
        Row { }
    }
}
