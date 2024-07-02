package viewModels

import cafe.adriel.voyager.core.model.ScreenModel
import repository.ArticleRepository

class ArticleViewModel(
    private val articleRepository: ArticleRepository
): ScreenModel {

    fun getArticle(articleId: Int) = articleRepository.getArticle(articleId)
}
