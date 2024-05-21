package viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import models.ArticleModel
import repository.ArticleRepository

class SearchViewModel(
    private val articleRepository: ArticleRepository
): ScreenModel {

    private var _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private var _articles = MutableStateFlow<List<ArticleModel>>(arrayListOf())
    val articles: StateFlow<List<ArticleModel>> = _articles

    init {
        getArticles()
    }

//    fun getArticles() = screenModelScope.launch {
//        _isLoading.value = true
//        delay(2000)
//        articleRepository.getArticles()
//        _isLoading.value = false
//    }

    fun getArticles(searchText: String? = null) = screenModelScope.launch {
        articleRepository.getArticles(searchText).collect {
            _articles.value = it
        }
    }
}
