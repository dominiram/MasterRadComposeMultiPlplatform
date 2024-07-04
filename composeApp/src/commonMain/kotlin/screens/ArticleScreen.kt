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
import androidx.compose.foundation.shape.CircleShape
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
import masterradcomposemultiplatform.composeapp.generated.resources.Res
import masterradcomposemultiplatform.composeapp.generated.resources.ic_schedule
import masterradcomposemultiplatform.composeapp.generated.resources.ic_visibility
import models.ArticleModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
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

        ArticleBottomInfoModal(modifier = Modifier.align(Alignment.BottomCenter), article)
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
                text = article.body,
                style = TextStyle(
                    fontSize = 13.sp,
                    color = Color.White
                )
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ArticleBottomInfoModal(modifier: Modifier, article: ArticleModel) {
    Column(
        modifier = modifier.clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        Row(modifier = Modifier) {

            ArticleInfoPill(
                imageUrl = article.authorImageUrl,
                icon = null,
                text = article.author,
                backgroundColor = Color.Black,
                textColor = Color.White
            )

            ArticleInfoPill(
                imageUrl = null,
                icon = Res.drawable.ic_schedule,
                text = article.author,
                backgroundColor = Color.LightGray,
                textColor = Color.Gray
            )
            ArticleInfoPill(
                imageUrl = null,
                icon = Res.drawable.ic_visibility,
                text = article.author,
                backgroundColor = Color.LightGray,
                textColor = Color.Gray
            )
        }

        Text(
            modifier = Modifier.padding(vertical = 12.dp),
            text = article.title,
            style = TextStyle(
                fontSize = 24.sp,
                color = Color.Black,
                fontWeight = FontWeight(700)
            )
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ArticleInfoPill(
    imageUrl: String?,
    icon: DrawableResource?,
    text: String,
    backgroundColor: Color,
    textColor: Color
) {
    Row(
        modifier = Modifier.background(
            color = backgroundColor,
            shape = RoundedCornerShape(24.dp)
        ).padding(vertical = 12.dp, horizontal = 8.dp)
    ) {
        imageUrl?.let { url ->
            KamelImage(
                modifier = Modifier.clip(CircleShape).height(24.dp).width(24.dp),
                resource = asyncPainterResource(url),
                contentDescription = null
            )
        }

        icon?.let {
            Icon(
                modifier = Modifier.height(24.dp).width(24.dp),
                painter = painterResource(it),
                tint = textColor,
                contentDescription = null
            )
        }

        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = text,
            style = TextStyle(
                color = textColor,
                fontSize = 14.sp
            )
        )
    }
}
