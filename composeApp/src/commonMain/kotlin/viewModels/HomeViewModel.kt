package viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import repository.ArticleRepository

class HomeViewModel(
    private val articleRepository: ArticleRepository
): ScreenModel {

    private var _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

//    fun getArticles() = screenModelScope.launch {
//        _isLoading.value = true
//        delay(2000)
//        articleRepository.getArticles()
//        _isLoading.value = false
//    }

    fun getArticles() = articleRepository.getStaticArticles()
}
