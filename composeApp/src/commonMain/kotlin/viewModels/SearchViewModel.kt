package viewModels

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import models.ArticleModel
import repository.ArticleRepository

class SearchViewModel(private val articleRepository: ArticleRepository): ScreenModel {

    private var _articles = MutableStateFlow<List<ArticleModel>>(arrayListOf())
    val articles: StateFlow<List<ArticleModel>> = _articles

    init {
        getArticles()
    }

    fun getArticles(searchText: String? = null) = screenModelScope.launch {
        articleRepository.getArticles(searchText).collect {
            _articles.value = it
        }
    }
}
