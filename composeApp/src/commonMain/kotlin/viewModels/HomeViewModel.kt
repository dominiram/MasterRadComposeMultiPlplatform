package viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import models.ArticleModel
import repository.ArticleRepository

class HomeViewModel(
    private val articleRepository: ArticleRepository
) : ScreenModel {

    private var _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private var _articles: MutableStateFlow<List<ArticleModel>> = MutableStateFlow(arrayListOf())
    val articles: StateFlow<List<ArticleModel>> = _articles

    private fun getArticles() = screenModelScope.launch {
        _isLoading.value = true
        _articles.emit(articleRepository.getStaticArticles())
        delay(2000)
        _isLoading.value = false
    }

    init {
        getArticles()
    }
}
